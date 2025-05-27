package com.darekbx.notebookcheckreader.domain

import com.darekbx.notebookcheckreader.repository.local.RssDao

class DeleteOldItemsUseCase(private val rssDao: RssDao) {

    suspend operator fun invoke() {
        val count = rssDao.fetchCountSync()
        if (count > MAX_ITEMS) {
            rssDao.deleteOldest(count - MAX_ITEMS)
        }
    }

    companion object {
        private const val MAX_ITEMS = 100
    }
}
