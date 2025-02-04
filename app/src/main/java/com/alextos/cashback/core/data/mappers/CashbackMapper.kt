package com.alextos.cashback.core.data.mappers

import com.alextos.cashback.core.data.combined_entities.CashbackWithCategory
import com.alextos.cashback.core.data.entities.CashbackEntity
import com.alextos.cashback.core.domain.Card
import com.alextos.cashback.core.domain.Cashback
import java.util.UUID

fun Cashback.toEntity(card: Card): CashbackEntity {
    return CashbackEntity(
        id = id.toString(),
        categoryId = category.id.toString(),
        percent = percent,
        cardId = card.id.toString()
    )
}

fun CashbackWithCategory.toDomain(): Cashback {
    return Cashback(
        id = UUID.fromString(cashbackEntity.id),
        category = categoryEntity.toDomain(),
        percent = cashbackEntity.percent
    )
}