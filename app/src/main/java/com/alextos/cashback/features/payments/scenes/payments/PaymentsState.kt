package com.alextos.cashback.features.payments.scenes.payments

import com.alextos.cashback.core.domain.models.Card
import com.alextos.cashback.core.domain.models.Payment
import java.time.LocalDate

data class PaymentsState(
    val allPayments: List<Payment> = listOf(),
    val periodPayments: List<Payment> = listOf(),
    val isAllTimePeriod: Boolean = false,
    val startPeriod: LocalDate = LocalDate.now(),
    val endPeriod: LocalDate = LocalDate.now(),
    val isNextButtonEnabled: Boolean = true,
    val isPreviousButtonEnabled: Boolean = true,
    val chartData: List<ChartData> = emptyList(),
    val currencyData: CurrencyData = CurrencyData()
) {
    fun isChartVisible(): Boolean {
        return if (isAllTimePeriod) allPayments.count() > 1 else periodPayments.count() > 1
    }
}

data class ChartData(
    val card: Card,
    val totalAmount: Int
)

data class CurrencyData(
    val rubles: Int = 0,
    val miles: Int = 0,
    val points: Int = 0
)