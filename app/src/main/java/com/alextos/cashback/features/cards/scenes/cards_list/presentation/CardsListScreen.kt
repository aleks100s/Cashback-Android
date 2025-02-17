package com.alextos.cashback.features.cards.scenes.cards_list.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alextos.cashback.BuildConfig
import com.alextos.cashback.R
import com.alextos.cashback.core.domain.models.Card
import com.alextos.cashback.common.views.Screen
import com.alextos.cashback.features.cards.scenes.cards_list.presentation.components.AddCardSheet
import com.alextos.cashback.features.cards.scenes.cards_list.presentation.components.CardItemView
import com.alextos.cashback.common.views.SearchBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardsListScreen(
    modifier: Modifier = Modifier,
    viewModel: CardsListViewModel,
    onCardSelect: (Card) -> Unit
) {
    val haptic = LocalHapticFeedback.current
    val state by viewModel.state.collectAsStateWithLifecycle()

    Screen(
        modifier = modifier,
        title = stringResource(R.string.cards_list_title),
        floatingActionButton = {
            FloatingActionButton(onClick = {
                viewModel.onAction(CardsListAction.AddCard)
            }) {
                Icon(Icons.Filled.Add, stringResource(R.string.cards_list_add_new_card))
            }
        }
    ) {
        CardsListView(modifier = it, state = state) { action ->
            when(action) {
                is CardsListAction.CardSelect -> onCardSelect(action.card)
                else -> Unit
            }
            viewModel.onAction(action)
        }

        if (state.isAddCardSheetShown) {
            ModalBottomSheet(onDismissRequest = {
                viewModel.onAction(CardsListAction.DismissAddCardSheet)
            }) {
                AddCardSheet(
                    cardName = state.newCardName,
                    onValueChange = { name ->
                        viewModel.onAction(CardsListAction.CardNameChange(name))
                    },
                    color = state.newCardColor,
                    onColorChange = { color ->
                        viewModel.onAction(CardsListAction.CardColorChange(color))
                    },
                    onSaveTapped = {
                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                        viewModel.onAction(CardsListAction.SaveButtonTapped)
                    }
                )
            }
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
                    SearchBar(
                        value = state.searchQuery,
                        placeholder = stringResource(R.string.cards_list_search_placeholder)
                    ) {
                        onAction(CardsListAction.SearchQueryChange(it))
                    }
                }
            }

            if (state.filteredCards.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            stringResource(R.string.cards_list_no_search_results),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            } else {
                items(state.filteredCards, key = { it.id }) { card ->
                    CardItemView(
                        modifier = Modifier.animateItem(),
                        card = card,
                        onClick = {
                            onAction(CardsListAction.CardSelect(card))
                        },
                        onFavouriteTap = {
                            onAction(CardsListAction.ToggleFavourite(card))
                        }
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(88.dp))
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