package com.alextos.cashback.features.cards.scenes.cards_list.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alextos.cashback.R
import com.alextos.cashback.common.views.CustomButton
import com.alextos.cashback.core.domain.models.Card
import com.alextos.cashback.common.views.Screen
import com.alextos.cashback.features.cards.scenes.cards_list.presentation.components.AddCardSheet
import com.alextos.cashback.features.cards.scenes.cards_list.presentation.components.CardItemView
import com.alextos.cashback.common.views.SearchBar
import com.alextos.cashback.common.views.FilterItemView
import com.alextos.cashback.common.views.CustomLabel
import com.alextos.cashback.common.views.EmptySearchView
import com.alextos.cashback.common.views.EmptyView
import com.alextos.cashback.features.cards.scenes.cards_list.presentation.components.CardsSettingsSheet

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
            Button(onClick = {
                viewModel.onAction(CardsListAction.AddCard)
            }) {
                CustomLabel(
                    title = stringResource(R.string.cards_list_add_new_card),
                    imageVector = Icons.Filled.Add
                )
            }
        },
        actions = {
            if (state.allCards.isNotEmpty()) {
                CustomButton(stringResource(R.string.card_list_edit_button)) {
                    viewModel.onAction(CardsListAction.EditButtonTapped)
                }
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

        if (state.isCardsSettingsSheetShown) {
            val sheetState = rememberModalBottomSheetState()

            ModalBottomSheet(
                onDismissRequest = {
                    viewModel.onAction(CardsListAction.DismissSettingsSheet)
                },
                dragHandle = null,
                sheetState = sheetState
            ) {
                CardsSettingsSheet(
                    state.isCompactViewActive,
                    onCheckedChange = { isActive ->
                        viewModel.onAction(CardsListAction.CompactViewToggle(isActive))
                    },
                    onDismiss = {
                        viewModel.onAction(CardsListAction.DismissSettingsSheet)
                    }
                )
            }
        }

        if (state.isAddCardSheetShown) {
            val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

            ModalBottomSheet(
                onDismissRequest = {
                    viewModel.onAction(CardsListAction.DismissAddCardSheet)
                },
                dragHandle = null,
                sheetState = sheetState
            ) {
                AddCardSheet(
                    cardName = state.newCardName,
                    onValueChange = { name ->
                        viewModel.onAction(CardsListAction.CardNameChange(name))
                    },
                    color = state.newCardColor,
                    onColorChange = { color ->
                        viewModel.onAction(CardsListAction.CardColorChange(color))
                    },
                    currency = state.newCardCurrency,
                    onCurrencyChange = { currency ->
                        viewModel.onAction(CardsListAction.CardCurrencyChange(currency))
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
    val focusManager = LocalFocusManager.current
    if (state.isLoading) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else if (state.allCards.isEmpty()) {
        EmptyView(
            modifier = modifier.padding(horizontal = 16.dp),
            title = stringResource(R.string.cards_list_empty),
            painter = painterResource(R.drawable.credit_card)
        )
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
                    Column(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.background.copy(alpha = 0.5f))
                            .padding(bottom = 4.dp)
                    ) {
                        SearchBar(
                            value = state.searchQuery,
                            placeholder = stringResource(R.string.cards_list_search_placeholder)
                        ) {
                            onAction(CardsListAction.SearchQueryChange(it))
                        }

                        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            items(state.popularCategories) { category ->
                                FilterItemView(
                                    category = category,
                                    isSelected = state.selectedCategory == category
                                ) {
                                    focusManager.clearFocus()
                                    onAction(CardsListAction.SelectCategory(category))
                                }
                            }
                        }
                    }
                }
            }

            if (state.filteredCards.isEmpty()) {
                item {
                    EmptySearchView(title = stringResource(R.string.cards_list_no_search_results))
                }
            } else {
                items(state.filteredCards, key = { it.id }) { card ->
                    CardItemView(
                        modifier = Modifier.animateItem(),
                        card = card,
                        onClick = {
                            onAction(CardsListAction.CardSelect(card))
                        },
                        query = state.searchQuery,
                        onFavouriteTap = {
                            onAction(CardsListAction.ToggleFavourite(card))
                        },
                        isCompact = state.isCompactViewActive
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