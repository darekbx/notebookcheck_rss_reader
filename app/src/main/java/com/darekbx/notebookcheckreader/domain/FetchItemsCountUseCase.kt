package com.darekbx.notebookcheckreader.domain

import com.darekbx.notebookcheckreader.repository.local.RssDao
import kotlinx.coroutines.flow.Flow

class FetchItemsCountUseCase(
    private val rssDao: RssDao
) {
    operator fun invoke(): Flow<Int> {
        return rssDao.fetchCount()
    }
}
