package com.darekbx.notebookcheckreader.di

import android.app.Application
import android.app.NotificationManager
import android.content.Context
import androidx.room.Room
import com.darekbx.notebookcheckreader.domain.AddRemoveToFavouritesUseCase
import com.darekbx.notebookcheckreader.domain.FetchFavouriteItemsUseCase
import com.darekbx.notebookcheckreader.domain.FetchFavouritesCountUseCase
import com.darekbx.notebookcheckreader.domain.FetchItemsCountUseCase
import com.darekbx.notebookcheckreader.domain.FetchRssItemsUseCase
import com.darekbx.notebookcheckreader.domain.MarkReadItemsUseCase
import com.darekbx.notebookcheckreader.domain.SynchronizeUseCase
import com.darekbx.notebookcheckreader.worker.KoinWorkerFactory
import com.darekbx.notebookcheckreader.repository.RssNotificationManager
import com.darekbx.notebookcheckreader.repository.local.AppDatabase
import com.darekbx.notebookcheckreader.repository.local.AppDatabase.Companion.MIGRATION_1_2
import com.darekbx.notebookcheckreader.repository.local.FavouritesDao
import com.darekbx.notebookcheckreader.repository.local.RssDao
import com.darekbx.notebookcheckreader.repository.remote.RssFetch
import com.darekbx.notebookcheckreader.repository.remote.RssParser
import com.darekbx.notebookcheckreader.ui.MainViewModel
import com.darekbx.notebookcheckreader.ui.favourites.FavouritesViewModel
import com.darekbx.notebookcheckreader.ui.news.NewsViewModel
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModule = module {
    single(named("feed_url")) { "https://www.notebookcheck.net/News.152.100.html" }

    factory {
        HttpClient(CIO) {
            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.BODY
            }
        }
    }
    single { RssParser() }
    factory { RssFetch(get(), get()) }

    // Local storage
    single<AppDatabase> {
        Room
            .databaseBuilder(get<Application>(), AppDatabase::class.java, AppDatabase.DB_NAME)
            .addMigrations(MIGRATION_1_2)
            .build()
    }
    single<RssDao> { get<AppDatabase>().rssDao() }
    single<FavouritesDao> { get<AppDatabase>().favouritesDao() }

    // Others
    single { androidContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager }
    factory { RssNotificationManager(androidContext(), get()) }
    single { KoinWorkerFactory(get(), get()) }
}

val domainModule = module {
    single { SynchronizeUseCase(get(), get(), get(named("feed_url"))) }
    single { FetchRssItemsUseCase(get(), get()) }
    single { FetchFavouriteItemsUseCase(get(), get()) }
    single { MarkReadItemsUseCase(get()) }
    single { AddRemoveToFavouritesUseCase(get()) }
    single { FetchFavouritesCountUseCase(get()) }
    single { FetchItemsCountUseCase(get()) }
}

val viewModelModule = module {
     viewModel { NewsViewModel(get(), get(), get()) }
     viewModel { MainViewModel(get(), get(), get()) }
     viewModel { FavouritesViewModel(get(), get()) }
}
