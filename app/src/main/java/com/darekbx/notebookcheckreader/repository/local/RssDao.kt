package com.darekbx.notebookcheckreader.repository.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface RssDao {

    @Insert
    suspend fun addAll(items: List<RssItemDto>)

    @Query("SELECT * FROM rss_item")
    suspend fun fetch(): List<RssItemDto>
}
