package com.darekbx.notebookcheckreader.domain

import com.darekbx.notebookcheckreader.model.RssItem
import com.darekbx.notebookcheckreader.repository.local.RssDao
import com.darekbx.notebookcheckreader.repository.toModel

class FetchRssItemsUseCase(private val rssDao: RssDao) {

    suspend operator fun invoke(): List<RssItem> {
        return rssDao.fetch().map { it.toModel() }
    }
}
