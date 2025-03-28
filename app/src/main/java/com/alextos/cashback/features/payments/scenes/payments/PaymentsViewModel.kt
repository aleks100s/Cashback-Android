package com.alextos.cashback.features.payments.scenes.payments

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class PaymentsViewModel: ViewModel() {
    private val _state = MutableStateFlow(PaymentsState())
    val state = _state.asStateFlow()

    fun onAction(action: PaymentsAction) {

    }
}