package com.alextos.cashback.features.payments.scenes.payments

import com.alextos.cashback.core.domain.models.Payment

data class PaymentsState(
    val payments: List<Payment> = listOf()
)
