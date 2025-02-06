package com.alextos.cashback.features.cards.presentation.card_detail

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CardDetailViewModel: ViewModel() {
    private val _state = MutableStateFlow(CardDetailState())
    val state = _state.asStateFlow()

    fun onAction(action: CardDetailAction) {
        when (action) {
            is CardDetailAction.ToggleEditMode -> {
                _state.update { it.copy(isEditMode = !it.isEditMode) }
            }
        }
    }
}