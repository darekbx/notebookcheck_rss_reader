package com.darekbx.notebookcheckreader.domain

import com.darekbx.notebookcheckreader.model.RssItem
import com.darekbx.notebookcheckreader.repository.local.FavouritesDao
import com.darekbx.notebookcheckreader.repository.local.RssDao
import com.darekbx.notebookcheckreader.repository.toModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FetchRssItemsUseCase(
    private val rssDao: RssDao,
    private val favouritesDao: FavouritesDao
) {

    operator fun invoke(): Flow<List<RssItem>> {
        return rssDao.fetch().map { list ->
            val favouriteItems = favouritesDao.fetch()
            list.map { item ->
                item.toModel().apply {
                    isFavourite = favouriteItems.any { favouriteItem ->
                        favouriteItem.itemId == this.localId
                    }
                }
            }
        }
    }
}
