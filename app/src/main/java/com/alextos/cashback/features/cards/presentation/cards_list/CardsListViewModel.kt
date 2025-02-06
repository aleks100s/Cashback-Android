package com.alextos.cashback.features.cards.presentation.cards_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alextos.cashback.R
import com.alextos.cashback.core.domain.services.ToastService
import com.alextos.cashback.features.cards.domain.CardsRepository
import com.alextos.cashback.features.cards.domain.use_cases.FilterUseCase
import com.alextos.cashback.util.UiText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CardsListViewModel(
    private val repository: CardsRepository,
    private val filterUseCase: FilterUseCase,
    private val toastService: ToastService
): ViewModel() {
    private val _state = MutableStateFlow(CardsListState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getAllCards()
                .collect { list ->
                    _state.update {
                        it.copy(
                            allCards = list,
                            filteredCards = filterUseCase.execute(list, it.searchQuery)
                        )
                    }
                }
        }
    }

    fun onAction(action: CardsListAction) {
        when (action) {
            is CardsListAction.SearchQueryChange -> {
                _state.update {
                    it.copy(
                        searchQuery = action.query,
                        filteredCards = filterUseCase.execute(it.allCards, action.query)
                    )
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
                toastService.showToast(
                    UiText.StringResourceId(
                        if (!action.card.isFavourite) {
                            R.string.cards_list_added_to_favourite
                        } else {
                            R.string.cards_list_removed_from_favourite
                        }
                    )
                )
            }
        }
    }
}