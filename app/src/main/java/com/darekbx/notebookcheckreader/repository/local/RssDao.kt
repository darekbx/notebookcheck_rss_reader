package com.darekbx.notebookcheckreader.repository.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface RssDao {

    @Insert
    suspend fun addAll(items: List<RssItemDto>)

    @Query("SELECT * FROM rss_item ORDER BY timestamp DESC")
    fun fetch(): Flow<List<RssItemDto>>

    @Query("SELECT * FROM rss_item ORDER BY timestamp DESC")
    suspend fun fetchAsync(): List<RssItemDto>
    
    @Query("SELECT * FROM rss_item WHERE id IN (:ids) ORDER BY timestamp DESC")
    suspend fun fetchByIds(ids: List<String>): List<RssItemDto>

    @Query("UPDATE rss_item SET isRead = 1 WHERE id = :uuid")
    suspend fun markAsRead(uuid: String)
}
