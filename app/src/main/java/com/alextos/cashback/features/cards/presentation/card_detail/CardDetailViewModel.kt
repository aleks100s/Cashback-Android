package com.alextos.cashback.features.cards.presentation.card_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.alextos.cashback.R
import com.alextos.cashback.core.domain.services.ToastService
import com.alextos.cashback.features.cards.CardsRoute
import com.alextos.cashback.features.cards.domain.CardsRepository
import com.alextos.cashback.util.UiText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CardDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val repository: CardsRepository,
    private val toastService: ToastService
): ViewModel() {
    private val cardId = savedStateHandle.toRoute<CardsRoute.CardDetail>().cardId

    private val _state = MutableStateFlow(CardDetailState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getCardFlow(cardId)
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
            is CardDetailAction.ShowDeleteCashbackDialog -> {
                _state.update { it.copy(cashbackToDelete = action.cashback) }
            }
            is CardDetailAction.DismissDeleteCashbackDialog -> {
                _state.update { it.copy(cashbackToDelete = null) }
            }
            is CardDetailAction.DeleteCashback -> {
                _state.update { it.copy(cashbackToDelete = null) }
                viewModelScope.launch(Dispatchers.IO) {
                    repository.deleteCashback(action.cashback, cardId)
                    repository.getCard(cardId)?.let { card ->
                        _state.update { it.copy(card = card) }
                        viewModelScope.launch(Dispatchers.Main) {
                            toastService.showToast(UiText.StringResourceId(R.string.card_detail_cashback_removed))
                        }
                    }
                }
            }
            is CardDetailAction.EditCashback -> {}
        }
    }
}