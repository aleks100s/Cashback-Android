package com.alextos.cashback.core.data.mappers

import com.alextos.cashback.core.data.combined_entities.CashbackWithCategory
import com.alextos.cashback.core.data.entities.CashbackEntity
import com.alextos.cashback.core.domain.models.Card
import com.alextos.cashback.core.domain.models.Cashback
import java.util.UUID

fun Cashback.toEntity(cardId: String): CashbackEntity {
    return CashbackEntity(
        id = id,
        categoryId = category.id,
        percent = percent,
        cardId = cardId
    )
}

fun CashbackWithCategory.toDomain(): Cashback {
    return Cashback(
        id = cashbackEntity.id,
        category = categoryEntity.toDomain(),
        percent = cashbackEntity.percent
    )
}