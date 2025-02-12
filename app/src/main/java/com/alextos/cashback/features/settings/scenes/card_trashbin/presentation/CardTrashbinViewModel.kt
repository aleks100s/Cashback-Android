package com.alextos.cashback.features.settings.scenes.card_trashbin.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alextos.cashback.R
import com.alextos.cashback.common.UiText
import com.alextos.cashback.core.domain.services.ToastService
import com.alextos.cashback.features.settings.scenes.card_trashbin.domain.CardTrashbinRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CardTrashbinViewModel(
    private val repository: CardTrashbinRepository,
    private val toastService: ToastService
): ViewModel() {
    private val _state = MutableStateFlow(CardTrashbinState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getArchivedCards()
                .collect { cards ->
                    _state.update { it.copy(cards = cards) }
                }
        }
    }

    fun onAction(action: CardTrashbinAction) {
        when (action) {
            is CardTrashbinAction.RestoreAll -> {
                viewModelScope.launch(Dispatchers.IO) {
                    state.value.cards.forEach {
                        repository.unarchive(it)
                    }
                }
                toastService.showToast(UiText.StringResourceId(R.string.trashbin_cards_restored))
            }
            is CardTrashbinAction.RestoreCard -> {
                viewModelScope.launch(Dispatchers.IO) {
                    repository.unarchive(action.card)
                }
                toastService.showToast(UiText.StringResourceId(R.string.trashbin_card_restored))
            }
        }
    }
}