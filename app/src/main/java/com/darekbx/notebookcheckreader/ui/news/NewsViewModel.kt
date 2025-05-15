package com.darekbx.notebookcheckreader.ui.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.darekbx.notebookcheckreader.domain.FetchRssItemsUseCase
import com.darekbx.notebookcheckreader.domain.MarkReadItemsUseCase
import com.darekbx.notebookcheckreader.model.RssItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface NewsUiState {
    object Loading : NewsUiState
    data class Error(val message: String) : NewsUiState
    data class Success(val items: List<RssItem>) : NewsUiState
}

class NewsViewModel(
    private val fetchRssItemsUseCase: FetchRssItemsUseCase,
    private val markReadItemsUseCase: MarkReadItemsUseCase
): ViewModel() {
    private val _uiState = MutableStateFlow<NewsUiState>(NewsUiState.Loading)
    val uiState: StateFlow<NewsUiState> = _uiState.asStateFlow()

    fun fetch() {
        viewModelScope.launch {
            _uiState.emit(NewsUiState.Loading)
            try {
                val items = fetchRssItemsUseCase()
                _uiState.emit(NewsUiState.Success(items))
            } catch (e: Exception) {
                _uiState.emit(NewsUiState.Error(e.message ?: "Unknown error"))
            }
        }
    }

    fun markAsRead(items: List<String>) {
        viewModelScope.launch {
            markReadItemsUseCase(items)
        }
    }
}
