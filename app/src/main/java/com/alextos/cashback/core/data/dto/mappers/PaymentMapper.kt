package com.alextos.cashback.core.data.dto.mappers

import com.alextos.cashback.core.data.dto.PaymentDto
import com.alextos.cashback.core.domain.models.Payment
import java.time.Instant
import java.time.LocalDate
import java.time.OffsetTime
import java.time.ZoneId
import java.time.ZoneOffset

fun Payment.toDto(): PaymentDto {
    val date = date.atTime(OffsetTime.now(ZoneId.systemDefault())).toEpochSecond()
    return PaymentDto(
        id = id,
        amount = amount,
        date = date,
        source = card?.toDto()
    )
}

fun PaymentDto.toDomain(): Payment {
    return Payment(
        id = id,
        amount = amount,
        date = Instant.ofEpochSecond(date).atZone(ZoneId.systemDefault()).toLocalDate(),
        card = source?.toDomain()
    )
}