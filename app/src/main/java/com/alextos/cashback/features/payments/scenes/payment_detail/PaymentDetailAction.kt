package com.alextos.cashback.features.payments.scenes.payment_detail

import com.alextos.cashback.core.domain.models.Card
import java.time.LocalDate

sealed interface PaymentDetailAction {
    data class CardSelected(val card: Card?): PaymentDetailAction
    data class AmountChanged(val text: String): PaymentDetailAction
    data class DateChanged(val date: LocalDate): PaymentDetailAction
    data object SaveButtonTapped: PaymentDetailAction
}