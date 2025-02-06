package com.alextos.cashback.features.cards.presentation.card_detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alextos.cashback.core.presentation.Screen

@Composable
fun CardDetailScreen(
    modifier: Modifier = Modifier,
    viewModel: CardDetailViewModel
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Screen(
        modifier = modifier,
        title = state.card?.name ?: ""
    ) {
        CardDetailView(
            modifier = modifier,
            state = state
        )
    }
}

@Composable
private fun CardDetailView(
    modifier: Modifier = Modifier,
    state: CardDetailState
) {
    Box(modifier = modifier.fillMaxSize()) {
        Text("Деталка карты")
    }
}

@Preview
@Composable
private fun CardDetailPreview() {
    CardDetailView(state = CardDetailState())
}