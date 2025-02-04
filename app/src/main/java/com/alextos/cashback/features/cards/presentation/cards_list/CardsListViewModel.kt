package com.alextos.cashback.features.cards.presentation.cards_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alextos.cashback.features.cards.domain.CategoryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CardsListViewModel(
    private val repository: CategoryRepository
): ViewModel() {
    private val _state = MutableStateFlow(CardsListState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getCategories()
                .collect { list ->
                    val names = list.map { it.name }
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
        }
    }
}