package com.alextos.cashback.core.data.entities.mappers

import com.alextos.cashback.core.data.entities.CardEntity
import com.alextos.cashback.core.domain.models.Card
import com.alextos.cashback.core.domain.models.Cashback
import com.alextos.cashback.core.domain.models.currency.Currency
import com.alextos.cashback.core.domain.models.currency.localization
import com.alextos.cashback.core.domain.models.currency.symbol
import java.util.UUID

fun Card.toEntity(): CardEntity {
    return CardEntity(
        id = id,
        name = name,
        color = color,
        isArchived = isArchived,
        isFavourite = isFavourite,
        currency = currency.localization,
        currencySymbol = currency.symbol
    )
}

fun CardEntity.toDomain(cashback: List<Cashback>): Card {
    return Card(
        id = id,
        name = name,
        cashback = cashback,
        color = color,
        isArchived = isArchived,
        isFavourite = isFavourite,
        currency = Currency.makeFrom(currency)
    )
}