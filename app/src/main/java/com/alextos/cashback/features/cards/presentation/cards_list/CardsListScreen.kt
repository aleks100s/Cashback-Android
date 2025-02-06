package com.alextos.cashback.features.cards.presentation.cards_list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alextos.cashback.R
import com.alextos.cashback.core.domain.models.Card
import com.alextos.cashback.features.cards.presentation.cards_list.components.CardItemView
import com.alextos.cashback.features.cards.presentation.cards_list.components.SearchBar

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
                    Text(stringResource(R.string.cards_list_title))
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun CardsListView(
    modifier: Modifier = Modifier,
    state: CardsListState,
    onAction: (CardsListAction) -> Unit
) {
    if (state.allCards.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = stringResource(R.string.cards_list_empty))
        }
    } else {
        val scrollState: LazyListState = rememberLazyListState()

        LazyColumn(
            modifier = modifier.padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            state = scrollState
        ) {
            stickyHeader {
                AnimatedVisibility(
                    visible = scrollState.lastScrolledBackward || !scrollState.canScrollBackward,
                    enter = fadeIn() + expandVertically(),
                    exit = fadeOut() + shrinkVertically()
                ) {
                    Surface(modifier = Modifier.padding(bottom = 8.dp)) {
                        SearchBar(state.searchQuery) {
                            onAction(CardsListAction.SearchQueryChange(it))
                        }
                    }
                }
            }

            if (state.filteredCards.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(stringResource(R.string.cards_list_no_search_results))
                    }
                }
            } else {
                items(state.filteredCards, key = { it.id }) { card ->
                    CardItemView(
                        modifier = Modifier
                            .animateItem()
                            .clickable {
                                onAction(CardsListAction.CardSelect(card))
                            },
                        card = card
                    ) {
                        onAction(CardsListAction.ToggleFavourite(card))
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

@Preview
@Composable
fun CardsListViewPreview() {
    CardsListView(state = CardsListState()) {}
}