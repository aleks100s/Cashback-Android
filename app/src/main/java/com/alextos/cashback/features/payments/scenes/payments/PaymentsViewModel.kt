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
import java.time.LocalDate

class PaymentsViewModel(
    private val paymentRepository: PaymentRepository,
    private val analyticsService: AnalyticsService
): ViewModel() {
    private val _state = MutableStateFlow(PaymentsState())
    val state = _state.asStateFlow()

    init {
        setupCurrentMonthPeriod()
        fetchPeriod()
    }

    fun onAction(action: PaymentsAction) {
        when (action) {
            is PaymentsAction.DeletePayment -> {
                analyticsService.logEvent(AnalyticsEvent.PaymentsDelete)
                viewModelScope.launch(Dispatchers.IO) {
                    paymentRepository.delete(payment = action.payment)
                }
            }
            is PaymentsAction.TogglePeriodMode -> {
                analyticsService.logEvent(AnalyticsEvent.PaymentsTogglePeriodButtonTapped)
                if (state.value.isAllTimePeriod) {
                    fetchPeriod()
                } else {
                    fetchAll()
                }
                _state.update { it.copy(isAllTimePeriod = !it.isAllTimePeriod) }
            }
        }
    }

    private fun setupCurrentMonthPeriod() {
        val now = LocalDate.now()
        val startOfMonth = now.withDayOfMonth(1)
        val endOfMonth = now.withDayOfMonth(now.lengthOfMonth())
        _state.update { it.copy(startPeriod = startOfMonth, endPeriod = endOfMonth) }
    }

    private fun fetchAll() {
        viewModelScope.launch(Dispatchers.IO) {
            val payments = paymentRepository.getAllPayments()
            _state.update { it.copy(payments = payments) }
        }
    }

    private fun fetchPeriod() {
        val start = state.value.startPeriod
        val end = state.value.endPeriod
        viewModelScope.launch(Dispatchers.IO) {
            val payments = paymentRepository.getPeriodPayments(start, end)
            _state.update { it.copy(payments = payments) }
        }
    }
}