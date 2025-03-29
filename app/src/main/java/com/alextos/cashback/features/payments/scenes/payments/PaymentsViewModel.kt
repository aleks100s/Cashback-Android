package com.alextos.cashback.features.payments.scenes.payments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alextos.cashback.core.domain.repository.PaymentRepository
import com.alextos.cashback.core.domain.services.AnalyticsEvent
import com.alextos.cashback.core.domain.services.AnalyticsService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PaymentsViewModel(
    private val paymentRepository: PaymentRepository,
    private val analyticsService: AnalyticsService
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
        when (action) {
            is PaymentsAction.DeletePayment -> {
                analyticsService.logEvent(AnalyticsEvent.PaymentsDelete)
                viewModelScope.launch(Dispatchers.IO) {
                    paymentRepository.delete(payment = action.payment)
                }
            }
        }
    }
}