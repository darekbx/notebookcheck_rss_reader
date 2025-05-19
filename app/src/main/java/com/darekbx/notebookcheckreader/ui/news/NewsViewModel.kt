package com.darekbx.notebookcheckreader.ui.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.darekbx.notebookcheckreader.domain.AddRemoveToFavouritesUseCase
import com.darekbx.notebookcheckreader.domain.FetchRssItemsUseCase
import com.darekbx.notebookcheckreader.domain.MarkReadItemsUseCase
import kotlinx.coroutines.launch

class NewsViewModel(
    private val fetchRssItemsUseCase: FetchRssItemsUseCase,
    private val markReadItemsUseCase: MarkReadItemsUseCase,
    private val addRemoveToFavouritesUseCase: AddRemoveToFavouritesUseCase
) : ViewModel() {
    fun itemsFlow() = fetchRssItemsUseCase()

    fun markAsRead(items: List<String>) {
        viewModelScope.launch {
            markReadItemsUseCase(items)
        }
    }

    fun markFavourite(itemId: String) {
        viewModelScope.launch {
            addRemoveToFavouritesUseCase(itemId)
        }
    }
}
