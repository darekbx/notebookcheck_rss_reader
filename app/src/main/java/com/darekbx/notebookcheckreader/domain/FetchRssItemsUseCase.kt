package com.darekbx.notebookcheckreader.domain

import com.darekbx.notebookcheckreader.model.RssItem
import com.darekbx.notebookcheckreader.repository.local.FavouritesDao
import com.darekbx.notebookcheckreader.repository.local.RssDao
import com.darekbx.notebookcheckreader.repository.toModel

class FetchRssItemsUseCase(
    private val rssDao: RssDao,
    private val favouritesDao: FavouritesDao
) {

    suspend operator fun invoke(): List<RssItem> {
        val favouriteItems = favouritesDao.fetch()
        return rssDao.fetch().map {
            it.toModel().apply {
                isFavourite = favouriteItems.any { favouriteItem ->
                    favouriteItem.itemId == this.localId
                }
            }
        }
    }
}
