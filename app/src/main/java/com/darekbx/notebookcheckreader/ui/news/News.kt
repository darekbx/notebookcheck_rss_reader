package com.darekbx.notebookcheckreader.ui.news

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.darekbx.notebookcheckreader.model.RssItem
import org.koin.androidx.compose.koinViewModel

@Composable
fun News(viewModel: NewsViewModel = koinViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.fetch()
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        when (val state = uiState) {
            is NewsUiState.Loading -> LoadingSpinner()
            is NewsUiState.Error -> ErrorMessage(state.message)
            is NewsUiState.Success -> Content(state.items)
        }
    }
}

@Composable
private fun Content(rssItems: List<RssItem>) {
    if (rssItems.isEmpty()) {
        Text(
            text = "No items available",
            style = MaterialTheme.typography.bodySmall,
        )
        return
    }
    LazyColumn(Modifier.fillMaxWidth()) {
        items(rssItems) { item ->
            Text(item.title)
        }
    }
}

@Composable
private fun LoadingSpinner() {
    CircularProgressIndicator(Modifier.size(64.dp))
}

@Composable
private fun ErrorMessage(message: String) {
    Text(
        text = message,
        color = MaterialTheme.colorScheme.error,
        style = MaterialTheme.typography.bodySmall,
    )
}
