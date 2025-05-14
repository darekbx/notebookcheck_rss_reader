package com.darekbx.notebookcheckreader.repository.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [RssItemDto::class], version = 1)
abstract class AppDatabase: RoomDatabase() {

    abstract fun rssDao(): RssDao

    companion object {
        const val DB_NAME = "rss_database"
    }
}
