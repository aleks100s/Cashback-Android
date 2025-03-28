package com.alextos.cashback.features.payments.scenes.payments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alextos.cashback.core.domain.repository.PaymentRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PaymentsViewModel(
    paymentRepository: PaymentRepository
): ViewModel() {
    private val _state = MutableStateFlow(PaymentsState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            paymentRepository.getAllPayments()
                .collect { payments ->
                    _state.update { it.copy(payments = payments) }
                }
        }
    }

    fun onAction(action: PaymentsAction) {

    }
}