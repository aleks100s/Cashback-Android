package com.alextos.cashback.features.cards.cards_list.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alextos.cashback.core.domain.Card

@Composable
fun CardsListScreen(
    modifier: Modifier,
    viewModel: CardsListViewModel,
    onCardSelect: (Card) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    CardsListView(modifier, state) { action ->
        when(action) {
            is CardsListAction.CardSelect -> onCardSelect(action.card)
            else -> Unit
        }
        viewModel.onAction(action)
    }
}

@Composable
fun CardsListView(
    modifier: Modifier = Modifier,
    state: CardsListState,
    onAction: (CardsListAction) -> Unit
) {
    val scrollState: LazyListState = rememberLazyListState()

    Column(
        modifier = modifier.fillMaxSize()
            .statusBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyColumn(
            state =  scrollState
        ) {
            items(state.allCards) { card ->
                Text(text = card.name)
            }
        }
    }
}

@Preview
@Composable
fun CardsListViewPreview() {
    CardsListView(state = CardsListState()) {}
}