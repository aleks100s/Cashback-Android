package com.alextos.cashback.core.data.mappers

import com.alextos.cashback.core.data.entities.CardEntity
import com.alextos.cashback.core.domain.models.Card
import com.alextos.cashback.core.domain.models.Cashback
import java.util.UUID

fun Card.toEntity(): CardEntity {
    return CardEntity(
        id = id.toString(),
        name = name,
        color = color,
        isArchived = isArchived,
        isFavourite = isFavourite,
        currency = currency,
        currencySymbol = currencySymbol
    )
}

fun CardEntity.toDomain(cashback: List<Cashback>): Card {
    return Card(
        id = UUID.fromString(id),
        name = name,
        cashback = cashback,
        color = color,
        isArchived = isArchived,
        isFavourite = isFavourite,
        currency = currency,
        currencySymbol = currencySymbol
    )
}