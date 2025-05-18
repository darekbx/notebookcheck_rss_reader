package com.darekbx.notebookcheckreader.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.darekbx.notebookcheckreader.domain.SynchronizeUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface MainUiState {
    object Idle : MainUiState
    object Loading : MainUiState
    data class Error(val message: String) : MainUiState
    data class Success(val count: Int) : MainUiState
}
class MainViewModel(
    private val synchronizeUseCase: SynchronizeUseCase
): ViewModel() {

    private val _uiState = MutableStateFlow<MainUiState>(MainUiState.Idle)
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()

    fun synchronize() {
        viewModelScope.launch {
            _uiState.value = MainUiState.Loading
            try {
                val itemsCount = synchronizeUseCase()
                _uiState.value = MainUiState.Success(itemsCount)
            } catch (e: Exception) {
                _uiState.value = MainUiState.Error(e.message ?: "Unknown error")
            }
        }
    }
}
