package com.alextos.cashback.core.data.entities.mappers

import com.alextos.cashback.core.data.entities.combined_entities.PaymentWithCard
import com.alextos.cashback.core.data.entities.PaymentEntity
import com.alextos.cashback.core.domain.models.Cashback
import com.alextos.cashback.core.domain.models.Payment
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.OffsetTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.util.UUID

fun Payment.toEntity(): PaymentEntity {
    return PaymentEntity(
        id = id,
        amount = amount,
        cardId = card?.id.toString(),
        date = date.atTime(OffsetTime.now(ZoneId.systemDefault())).toEpochSecond()
    )
}

fun PaymentWithCard.toDomain(cashback: List<Cashback>): Payment {
    return Payment(
        id = payment.id,
        amount = payment.amount,
        card = card.toDomain(cashback),
        date = Instant.ofEpochSecond(payment.date).atZone(ZoneId.systemDefault()).toLocalDate()
    )
}