package com.alextos.cashback.features.payments.scenes.payments

import com.alextos.cashback.core.domain.models.Payment

sealed interface PaymentsAction {
    data class DeletePayment(val payment: Payment): PaymentsAction
    data object TogglePeriodMode: PaymentsAction
    data object NextMonth: PaymentsAction
    data object PreviousMonth: PaymentsAction
    data class PaymentSelected(val payment: Payment): PaymentsAction
    data object AddButtonTapped: PaymentsAction
}