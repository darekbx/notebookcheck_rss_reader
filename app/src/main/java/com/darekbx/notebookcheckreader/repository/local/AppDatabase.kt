package com.darekbx.notebookcheckreader.repository.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [RssItemDto::class, FavouriteItemDto::class], version = 2)
abstract class AppDatabase : RoomDatabase() {

    abstract fun rssDao(): RssDao

    abstract fun favouritesDao(): FavouritesDao

    companion object {
        const val DB_NAME = "rss_database"

        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(
                    "CREATE TABLE IF NOT EXISTS `favourite_items` (" +
                            "`id` TEXT PRIMARY KEY NOT NULL, " +
                            "`itemId` TEXT NOT NULL)"
                )
            }
        }
    }
}
