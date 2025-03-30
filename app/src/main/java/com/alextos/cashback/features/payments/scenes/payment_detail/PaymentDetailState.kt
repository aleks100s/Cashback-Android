package com.alextos.cashback.features.payments.scenes.payment_detail

import com.alextos.cashback.core.domain.models.Card
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

data class PaymentDetailState(
    val amount: String = "0",
    val availableCards: List<Card> = emptyList(),
    val selectedCard: Card? = null,
    val date: LocalDate = LocalDate.now(),
    val isEditMode: Boolean = false
) {
    fun isEnabled(): Boolean {
        return (amount.toIntOrNull() ?: 0) > 0 && selectedCard != null
    }

    fun dateString(): String {
        val formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale("ru_RU"))
        return formatter.format(date)
    }
}
