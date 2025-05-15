package com.darekbx.notebookcheckreader.domain

import com.darekbx.notebookcheckreader.repository.local.RssDao

class MarkReadItemsUseCase(private val rssDao: RssDao) {

    suspend operator fun invoke(ids: List<String>) {
        ids.forEach { rssDao.markAsRead(it) }
    }
}
