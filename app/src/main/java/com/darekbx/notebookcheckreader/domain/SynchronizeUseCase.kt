package com.darekbx.notebookcheckreader.domain

import com.darekbx.notebookcheckreader.repository.local.RssDao
import com.darekbx.notebookcheckreader.repository.remote.RssFetch
import com.darekbx.notebookcheckreader.repository.toDto

class SynchronizeUseCase(
    private val rssFetch: RssFetch,
    private val rssDao: RssDao,
    private val feedUrl: String
)  {

    /**
     * Synchronizes the local database with the remote RSS feed.
     * @return Number of new items added to the database
     * @throws Exception
     */
    suspend operator fun invoke(): Int {
        // 1. Fetch local items
        val existingItems = rssDao.fetchAsync()

        // 2. Fetch new items from the feed
        val items = rssFetch.fetch(feedUrl)

        // 3. Filter out existing items
        val newItems = items.filter { item ->
            existingItems.none { existingItem ->
                existingItem.link == item.link
            }
        }

        // 4. Save new items to the database
        rssDao.addAll(newItems.map { it.toDto() })

        return newItems.size
    }
}
