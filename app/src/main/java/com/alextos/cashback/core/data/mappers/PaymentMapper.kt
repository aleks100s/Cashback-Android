package com.alextos.cashback.core.data.mappers

import com.alextos.cashback.core.data.combined_entities.PaymentWithCard
import com.alextos.cashback.core.data.entities.PaymentEntity
import com.alextos.cashback.core.domain.models.Cashback
import com.alextos.cashback.core.domain.models.Payment
import java.time.LocalDate
import java.util.UUID

fun Payment.toEntity(): PaymentEntity {
    return PaymentEntity(
        id = id,
        amount = amount,
        cardId = card?.id.toString(),
        date = date.toEpochDay()
    )
}

fun PaymentWithCard.toDomain(cashback: List<Cashback>): Payment {
    return Payment(
        id = payment.id,
        amount = payment.amount,
        card = card.toDomain(cashback),
        date = LocalDate.ofEpochDay(payment.date)
    )
}