package com.alextos.cashback.features.cards.cards_list.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CardsListViewModel: ViewModel() {
    private val _state = MutableStateFlow(CardsListState())
    val state = _state.asStateFlow()

    fun onAction(action: CardsListAction) {
        when (action) {
            is CardsListAction.SearchQueryChange -> {
                _state.update {
                    it.copy(searchQuery = action.query)
                }
            }
            is CardsListAction.CardSelect -> {
                //
            }
        }
    }
}