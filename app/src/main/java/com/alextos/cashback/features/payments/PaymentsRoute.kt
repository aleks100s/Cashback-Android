package com.alextos.cashback.features.payments

import kotlinx.serialization.Serializable

sealed interface PaymentsRoute {
    @Serializable
    data object PaymentsGraph: PaymentsRoute

    @Serializable
    data object Payments: PaymentsRoute

    @Serializable
    data class PaymentDetail(val paymentId: String?): PaymentsRoute
}