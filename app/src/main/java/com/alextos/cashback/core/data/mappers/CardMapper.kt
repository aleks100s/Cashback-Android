package com.alextos.cashback.core.data.mappers

import com.alextos.cashback.core.data.entities.CardEntity
import com.alextos.cashback.core.domain.models.Card
import com.alextos.cashback.core.domain.models.Cashback
import java.util.UUID

fun Card.toEntity(): CardEntity {
    return CardEntity(
        id = id,
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
        id = id,
        name = name,
        cashback = cashback.sortedBy { it.category.name },
        color = color,
        isArchived = isArchived,
        isFavourite = isFavourite,
        currency = currency,
        currencySymbol = currencySymbol
    )
}