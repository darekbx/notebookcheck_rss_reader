package com.darekbx.notebookcheckreader.domain

import com.darekbx.notebookcheckreader.model.RssItem
import com.darekbx.notebookcheckreader.repository.local.FavouritesDao
import com.darekbx.notebookcheckreader.repository.local.RssDao
import com.darekbx.notebookcheckreader.repository.toModel

class FetchFavouriteItemsUseCase(
    private val rssDao: RssDao,
    private val favouritesDao: FavouritesDao
) {
    suspend operator fun invoke(): List<RssItem> {
        val favouriteItems = favouritesDao.fetch()
        if (favouriteItems.isEmpty()) {
            return emptyList()
        }
        
        val favouriteItemIds = favouriteItems.map { it.itemId }
        return rssDao.fetchByIds(favouriteItemIds).map { 
            it.toModel().apply {
                isFavourite = true
            }
        }
    }
}
