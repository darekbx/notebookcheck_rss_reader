package com.darekbx.notebookcheckreader

import android.app.Application
import androidx.work.Configuration
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.darekbx.notebookcheckreader.worker.DataRefreshWorker
import com.darekbx.notebookcheckreader.worker.KoinWorkerFactory
import com.darekbx.notebookcheckreader.di.appModule
import com.darekbx.notebookcheckreader.di.domainModule
import com.darekbx.notebookcheckreader.di.viewModelModule
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import java.util.concurrent.TimeUnit
import kotlin.getValue

class MainApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@MainApplication)
            modules(appModule, viewModelModule, domainModule)
        }

        val workerFactory: KoinWorkerFactory by inject()
        WorkManager.initialize(
            this,
            Configuration.Builder()
                .setWorkerFactory(workerFactory)
                .build()
        )

        schedulePeriodicDataRefresh()
    }

    private fun schedulePeriodicDataRefresh() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val dataRefreshWorkRequest = PeriodicWorkRequestBuilder<DataRefreshWorker>(8, TimeUnit.HOURS)
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "DataRefreshWork",
            ExistingPeriodicWorkPolicy.KEEP,
            dataRefreshWorkRequest
        )
    }
}