package com.alextos.cashback.features.cards.presentation.cards_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alextos.cashback.features.cards.domain.CardsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CardsListViewModel(
    private val repository: CardsRepository
): ViewModel() {
    private val _state = MutableStateFlow(CardsListState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getAllCards()
                .collect { list ->
                    _state.update {
                        it.copy(allCards = list)
                    }
                }
        }
    }

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
            is CardsListAction.ToggleFavourite -> {
                viewModelScope.launch(Dispatchers.IO) {
                    val card = action.card
                    repository.update(card.copy(isFavourite = !card.isFavourite))
                }
            }
        }
    }
}