package com.darekbx.notebookcheckreader.domain

import com.darekbx.notebookcheckreader.repository.local.FavouriteItemDto
import com.darekbx.notebookcheckreader.repository.local.FavouritesDao

class AddRemoveToFavouritesUseCase(private val favouritesDao: FavouritesDao) {

    suspend operator fun invoke(rssItemId: String) {
        favouritesDao.getById(rssItemId)
            ?.let { favouritesDao.delete(rssItemId) }
            ?: run { favouritesDao.add(FavouriteItemDto(itemId = rssItemId)) }
    }
}
