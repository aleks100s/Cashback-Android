package com.alextos.cashback.features.cards.presentation.add_cashback

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AddCashbackViewModel: ViewModel() {
    private val _state = MutableStateFlow(AddCashbackState())
    val state = _state.asStateFlow()

    fun onAction(action: AddCashbackAction) {
        when(action) {
            is AddCashbackAction.ChangePercent -> {
                _state.update { it.copy(percent = action.value) }
            }
            is AddCashbackAction.SaveCashback -> {

            }
            is AddCashbackAction.SelectCategory -> {}
        }
    }
}