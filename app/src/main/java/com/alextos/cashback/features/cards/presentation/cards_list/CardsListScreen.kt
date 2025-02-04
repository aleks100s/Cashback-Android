package com.alextos.cashback.features.cards.presentation.cards_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alextos.cashback.core.domain.Card
import com.alextos.cashback.features.cards.presentation.cards_list.components.CardItemView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardsListScreen(
    modifier: Modifier = Modifier,
    viewModel: CardsListViewModel,
    onCardSelect: (Card) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text("Мои карты")
                }
            )
        }
    ) { innerPaddings ->
        CardsListView(
            modifier = Modifier.padding(top = innerPaddings.calculateTopPadding()),
            state = state
        ) { action ->
            when(action) {
                is CardsListAction.CardSelect -> onCardSelect(action.card)
                else -> Unit
            }
            viewModel.onAction(action)
        }
    }
}

@Composable
private fun CardsListView(
    modifier: Modifier = Modifier,
    state: CardsListState,
    onAction: (CardsListAction) -> Unit
) {
    if (state.allCards.isEmpty()) {
        Text(text = "Нет сохраненных карт")
    } else {
        val scrollState: LazyListState = rememberLazyListState()

        LazyColumn(
            modifier = modifier
                .padding(horizontal = 12.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            state = scrollState
        ) {
            items(state.allCards) { card ->
                CardItemView(
                    modifier = Modifier.clickable {
                        onAction(CardsListAction.CardSelect(card))
                    },
                    card = card
                )
            }
            item {
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Preview
@Composable
fun CardsListViewPreview() {
    CardsListView(state = CardsListState()) {}
}