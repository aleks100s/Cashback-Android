package com.alextos.cashback.features.payments.scenes.payments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alextos.cashback.core.domain.models.Card
import com.alextos.cashback.core.domain.models.currency.Currency
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

    private var allPaymentsChart: List<ChartData> = emptyList()
    private var periodChart: List<ChartData> = emptyList()
    private var allCurrencyData = CurrencyData()
    private var periodCurrencyData = CurrencyData()

    private var job: Job? = null

    init {
        setupCurrentMonthPeriod()

        viewModelScope.launch(Dispatchers.IO) {
            period.collect { period ->
                job?.cancelAndJoin()
                job = viewModelScope.launch(Dispatchers.IO) {
                    paymentRepository.getPeriodPayments(period.start, period.end).collect { payments ->
                        _state.update { it.copy(periodPayments = payments, startPeriod = period.start, endPeriod = period.end) }
                        updatePeriodPaymentsChart()
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
                    updateAllPaymentsChart()
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
                _state.update { state ->
                    state.copy(
                        isAllTimePeriod = !state.isAllTimePeriod,
                        chartData = if (!state.isAllTimePeriod) allPaymentsChart else periodChart,
                        currencyData = if (!state.isAllTimePeriod) allCurrencyData else periodCurrencyData,
                    )
                }
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

    private fun updateAllPaymentsChart() {
        val payments = state.value.allPayments
        val data = mutableMapOf<Card, Int>()
        payments.forEach { payment ->
            val card = payment.card ?: return@forEach

            data[card] = (data[card] ?: 0) + payment.amount
        }
        val chartData = data.map { ChartData(card = it.key, totalAmount = it.value) }.sortedBy { it.totalAmount }
        val currencyData = chartData.groupBy({ it.card.currency }, { it.totalAmount })
        allCurrencyData = CurrencyData(
            rubles = currencyData[Currency.RUBLE]?.sum() ?: 0,
            miles = currencyData[Currency.MILES]?.sum() ?: 0,
            points = currencyData[Currency.POINTS]?.sum() ?: 0
        )
        allPaymentsChart = chartData
        if (state.value.isAllTimePeriod) {
            _state.update { state ->
                state.copy(
                    chartData = allPaymentsChart,
                    currencyData = allCurrencyData
                )
            }
        }
    }

    private fun updatePeriodPaymentsChart() {
        val payments = state.value.periodPayments
        val data = mutableMapOf<Card, Int>()
        payments.forEach { payment ->
            val card = payment.card ?: return@forEach

            data[card] = (data[card] ?: 0) + payment.amount
        }
        val chartData = data.map { ChartData(card = it.key, totalAmount = it.value) }.sortedBy { it.totalAmount }
        val currencyData = chartData.groupBy({ it.card.currency }, { it.totalAmount })
        periodCurrencyData = CurrencyData(
            rubles = currencyData[Currency.RUBLE]?.sum() ?: 0,
            miles = currencyData[Currency.MILES]?.sum() ?: 0,
            points = currencyData[Currency.POINTS]?.sum() ?: 0
        )
        periodChart = chartData
        if (!state.value.isAllTimePeriod) {
            _state.update { state ->
                state.copy(
                    chartData = periodChart,
                    currencyData = periodCurrencyData
                )
            }
        }
    }
}

data class Period(
    val start: LocalDate,
    val end: LocalDate
)