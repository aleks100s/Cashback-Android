package com.alextos.cashback.features.payments.scenes.payments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alextos.cashback.core.domain.repository.PaymentRepository
import com.alextos.cashback.core.domain.services.AnalyticsEvent
import com.alextos.cashback.core.domain.services.AnalyticsService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
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

    private val period = MutableStateFlow(Period(LocalDate.now(), LocalDate.now()))
    private var firstDate = LocalDate.now()
    private var lastDate = LocalDate.now()

    private var job: Job? = null

    init {
        setupCurrentMonthPeriod()

        viewModelScope.launch(Dispatchers.IO) {
            period.collect { period ->
                job?.cancelAndJoin()
                job = viewModelScope.launch(Dispatchers.IO) {
                    paymentRepository.getPeriodPayments(period.start, period.end).collect { payments ->
                        _state.update { it.copy(periodPayments = payments, startPeriod = period.start, endPeriod = period.end) }
                    }
                }
            }
        }

        viewModelScope.launch(Dispatchers.IO) {
            paymentRepository.getAllPaymentsFlow()
                .collect { payments ->
                    _state.update { it.copy(allPayments = payments) }
                    firstDate = payments.firstOrNull()?.date ?: LocalDate.now()
                    lastDate = payments.lastOrNull()?.date ?: LocalDate.now()
                    updateButtons()
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
            is PaymentsAction.TogglePeriodMode -> {
                analyticsService.logEvent(AnalyticsEvent.PaymentsTogglePeriodButtonTapped)
                _state.update { it.copy(isAllTimePeriod = !it.isAllTimePeriod) }
            }
            is PaymentsAction.PreviousMonth -> {
                analyticsService.logEvent(AnalyticsEvent.PaymentsPreviousButtonTapped)
                previousMonth()
            }
            is PaymentsAction.NextMonth -> {
                analyticsService.logEvent(AnalyticsEvent.PaymentsNextButtonTapped)
                nextMonth()
            }
            is PaymentsAction.PaymentSelected -> {
                analyticsService.logEvent(AnalyticsEvent.PaymentsSelect)
            }
            is PaymentsAction.AddButtonTapped -> {
                analyticsService.logEvent(AnalyticsEvent.PaymentsAddPaymentButtonTapped)
            }
        }
    }

    private fun setupCurrentMonthPeriod() {
        val now = LocalDate.now()
        val startOfMonth = now.withDayOfMonth(1)
        val endOfMonth = now.withDayOfMonth(now.lengthOfMonth())
        period.update { it.copy(start = startOfMonth, end = endOfMonth) }
        updateButtons()
    }

    private fun previousMonth() {
        val end = period.value.end.minusMonths(1)
        period.update { it.copy(start = it.start.minusMonths(1), end = end.withDayOfMonth(end.lengthOfMonth())) }
        updateButtons()
    }

    private fun nextMonth() {
        val end = period.value.end.plusMonths(1)
        period.update { it.copy(start = it.start.plusMonths(1), end = end.withDayOfMonth(end.lengthOfMonth())) }
        updateButtons()
    }

    private fun updateButtons() {
        _state.update { state ->
            state.copy(
                isNextButtonEnabled = period.value.end <= lastDate,
                isPreviousButtonEnabled = period.value.start > firstDate
            )
        }
    }
}

data class Period(
    val start: LocalDate,
    val end: LocalDate
)