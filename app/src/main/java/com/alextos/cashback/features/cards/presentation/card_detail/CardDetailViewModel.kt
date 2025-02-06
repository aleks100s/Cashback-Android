package com.alextos.cashback.features.cards.presentation.card_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.alextos.cashback.features.cards.CardsRoute
import com.alextos.cashback.features.cards.domain.CardsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CardDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val repository: CardsRepository
): ViewModel() {
    private val cardId = savedStateHandle.toRoute<CardsRoute.CardDetail>().cardId

    private val _state = MutableStateFlow(CardDetailState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getCard(cardId)
                .collect { card ->
                    _state.update { it.copy(card = card) }
                }
        }
    }

    fun onAction(action: CardDetailAction) {
        when (action) {
            is CardDetailAction.ToggleEditMode -> {
                _state.update { it.copy(isEditMode = !it.isEditMode) }
            }
            is CardDetailAction.AddCashback -> {

            }
        }
    }
}