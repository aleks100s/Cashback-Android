package com.alextos.cashback.core.data.dto.mappers

import com.alextos.cashback.core.data.dto.PaymentDto
import com.alextos.cashback.core.domain.models.Payment
import java.time.LocalDate
import java.time.ZoneOffset

fun Payment.toDto(): PaymentDto {
    val referenceDate = LocalDate.of(2001, 1, 1).atStartOfDay().toEpochSecond(ZoneOffset.UTC)
    val date = date.atStartOfDay().toEpochSecond(ZoneOffset.UTC) - referenceDate
    return PaymentDto(
        id = id,
        amount = amount,
        date = date.toDouble(),
        source = card?.toDto()
    )
}

fun PaymentDto.toDomain(): Payment {
    return Payment(
        id = id,
        amount = amount,
        date = LocalDate.of(2001, 1, 1).atStartOfDay().plusSeconds(date.toLong()).toLocalDate(),
        card = source?.toDomain()
    )
}