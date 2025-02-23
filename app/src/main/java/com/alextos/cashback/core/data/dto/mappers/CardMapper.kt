package com.alextos.cashback.core.data.dto.mappers

import com.alextos.cashback.core.data.dto.CardDto
import com.alextos.cashback.core.domain.models.Card
import com.alextos.cashback.core.domain.models.currency.Currency
import com.alextos.cashback.core.domain.models.currency.localization
import com.alextos.cashback.core.domain.models.currency.symbol

fun Card.toDto(): CardDto {
    return CardDto(
        id = id,
        name = name,
        cashback = cashback.map { it.toDto() },
        color = color,
        isArchived = isArchived,
        isFavorite = isFavourite,
        currency = currency.localization,
        currencySymbol = currency.symbol
    )
}

fun CardDto.toDomain(): Card {
    return Card(
        id = id,
        name = name,
        cashback = cashback.map { it.toDomain() },
        color = color,
        isArchived = isArchived,
        isFavourite = isFavorite,
        currency = Currency.makeFrom(currency)
    )
}