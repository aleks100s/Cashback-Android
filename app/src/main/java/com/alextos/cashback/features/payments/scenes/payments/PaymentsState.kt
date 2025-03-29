package com.alextos.cashback.features.payments.scenes.payments

import com.alextos.cashback.core.domain.models.Payment
import java.time.LocalDate

data class PaymentsState(
    val payments: List<Payment> = listOf(),
    val isAllTimePeriod: Boolean = false,
    val startPeriod: LocalDate = LocalDate.now(),
    val endPeriod: LocalDate = LocalDate.now()
)
