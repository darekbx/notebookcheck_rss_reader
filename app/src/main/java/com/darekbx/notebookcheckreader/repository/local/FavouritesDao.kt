package com.darekbx.notebookcheckreader.repository.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavouritesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(item: FavouriteItemDto)

    @Query("DELETE FROM favourite_items WHERE itemId = :rssItemId")
    suspend fun delete(rssItemId: String)

    @Query("SELECT * FROM favourite_items WHERE itemId = :rssItemId")
    suspend fun getById(rssItemId: String): FavouriteItemDto?

    @Query("SELECT * FROM favourite_items")
    suspend fun fetch(): List<FavouriteItemDto>
}
