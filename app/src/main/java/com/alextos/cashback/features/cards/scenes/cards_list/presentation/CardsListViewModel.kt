package com.alextos.cashback.features.cards.scenes.cards_list.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alextos.cashback.R
import com.alextos.cashback.core.domain.models.Card
import com.alextos.cashback.core.domain.services.ToastService
import com.alextos.cashback.core.domain.repository.CardsRepository
import com.alextos.cashback.features.cards.scenes.cards_list.domain.FilterCardsUseCase
import com.alextos.cashback.common.UiText
import com.alextos.cashback.core.AppConstants
import com.alextos.cashback.core.domain.models.currency.Currency
import com.alextos.cashback.core.domain.models.currency.localization
import com.alextos.cashback.core.domain.models.currency.symbol
import com.alextos.cashback.core.domain.repository.CategoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CardsListViewModel(
    private val cardsRepository: CardsRepository,
    private val categoryRepository: CategoryRepository,
    private val filterUseCase: FilterCardsUseCase,
    private val toastService: ToastService
): ViewModel() {
    private val _state = MutableStateFlow(CardsListState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            cardsRepository.getAllCards()
                .collect { list ->
                    _state.update {
                        it.copy(
                            allCards = list,
                            filteredCards = filterUseCase.execute(list, it.searchQuery)
                        )
                    }
                }
        }

        viewModelScope.launch {
            categoryRepository.getPopularCategories()
                .collect { list ->
                    _state.update { it.copy(popularCategories = list, selectedCategory = null) }
                }
        }
    }

    fun onAction(action: CardsListAction) {
        when (action) {
            is CardsListAction.SearchQueryChange -> {
                _state.update { it.copy(searchQuery = action.query, selectedCategory = null) }
                viewModelScope.launch(Dispatchers.IO) {
                    _state.update {
                        it.copy(filteredCards = filterUseCase.execute(it.allCards, action.query))
                    }
                }
            }
            is CardsListAction.ToggleFavourite -> {
                viewModelScope.launch(Dispatchers.IO) {
                    val card = action.card
                    cardsRepository.createOrUpdate(card.copy(isFavourite = !card.isFavourite))
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
            is CardsListAction.AddCard -> {
                _state.update {
                    it.copy(isAddCardSheetShown = true)
                }
            }
            is CardsListAction.DismissAddCardSheet -> {
                _state.update {
                    it.copy(isAddCardSheetShown = false)
                }
            }
            is CardsListAction.CardNameChange -> {
                _state.update {
                    it.copy(newCardName = action.name)
                }
            }
            is CardsListAction.CardColorChange -> {
                _state.update {
                    it.copy(newCardColor = "#${action.color}")
                }
            }
            is CardsListAction.CardCurrencyChange -> {
                _state.update {
                    it.copy(newCardCurrency = action.currency)
                }
            }
            is CardsListAction.SaveButtonTapped -> {
                viewModelScope.launch(Dispatchers.IO) {
                    val card = Card(
                        name = state.value.newCardName,
                        color = state.value.newCardColor,
                        currency = state.value.newCardCurrency.localization,
                        currencySymbol = state.value.newCardCurrency.symbol
                    )
                    cardsRepository.createOrUpdate(card)
                    _state.update { state ->
                        state.copy(
                            newCardName = "",
                            newCardColor = AppConstants.COLOR_HEX_DEFAULT,
                            newCardCurrency = Currency.RUBLE,
                            isAddCardSheetShown = false
                        )
                    }
                    withContext(Dispatchers.Main) {
                        toastService.showToast(UiText.StringResourceId(R.string.cards_list_card_added))
                    }
                }
            }
            is CardsListAction.SelectCategory -> {
                val category = action.category
                if (category == state.value.selectedCategory) {
                    _state.update { it.copy(selectedCategory = null, filteredCards = it.allCards) }
                } else {
                    _state.update { it.copy(selectedCategory = category, searchQuery = "") }
                    viewModelScope.launch(Dispatchers.IO) {
                        _state.update {
                            it.copy(filteredCards = filterUseCase.execute(it.allCards, category.name))
                        }
                    }
                }
            }
            is CardsListAction.CardSelect -> {}
        }
    }
}