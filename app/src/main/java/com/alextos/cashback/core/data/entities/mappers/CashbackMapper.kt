package com.alextos.cashback.core.data.entities.mappers

import com.alextos.cashback.core.data.entities.combined_entities.CashbackWithCategory
import com.alextos.cashback.core.data.entities.CashbackEntity
import com.alextos.cashback.core.domain.models.Cashback

fun Cashback.toEntity(cardId: String): CashbackEntity {
    return CashbackEntity(
        id = id,
        categoryId = category.id,
        percent = percent,
        cardId = cardId,
        order = order
    )
}

fun CashbackWithCategory.toDomain(): Cashback {
    return Cashback(
        id = cashbackEntity.id,
        category = categoryEntity.toDomain(),
        percent = cashbackEntity.percent,
        order = cashbackEntity.order
    )
}