package com.alextos.cashback.features.cards.presentation.add_cashback

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alextos.cashback.core.domain.models.Cashback
import com.alextos.cashback.features.cards.domain.CardsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID

class AddCashbackViewModel(
    private val cardsRepository: CardsRepository
): ViewModel() {
    private val _state = MutableStateFlow(AddCashbackState())
    val state = _state.asStateFlow()

    fun onAction(action: AddCashbackAction) {
        when(action) {
            is AddCashbackAction.ChangePercent -> {
                _state.update { it.copy(percent = action.value) }
            }
            is AddCashbackAction.SaveCashback -> {
                state.value.selectedCategory?.let { category ->
                    val cashback = Cashback(id = UUID.randomUUID(), category = category, percent = state.value.percent)
                    viewModelScope.launch(Dispatchers.IO) {
                        cardsRepository.createCashback(cashback = cashback, cardId = "")
                    }
                }
            }
            is AddCashbackAction.SelectCategory -> {}
        }
    }
}