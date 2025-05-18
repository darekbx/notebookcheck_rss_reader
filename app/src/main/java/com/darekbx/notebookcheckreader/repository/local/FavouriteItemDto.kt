package com.darekbx.notebookcheckreader.repository.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "favourite_items")
data class FavouriteItemDto(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val itemId: String
)
