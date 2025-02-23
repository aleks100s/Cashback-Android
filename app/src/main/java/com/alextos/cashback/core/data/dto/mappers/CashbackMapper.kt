package com.alextos.cashback.core.data.dto.mappers

import com.alextos.cashback.core.data.dto.CashbackDto
import com.alextos.cashback.core.domain.models.Cashback

fun Cashback.toDto(): CashbackDto {
    return CashbackDto(
        id = id,
        category = category.toDto(),
        percent = percent,
        order = order
    )
}

fun CashbackDto.toDomain(): Cashback {
    return Cashback(
        id = id,
        category = category.toDomain(),
        percent = percent,
        order = order
    )
}