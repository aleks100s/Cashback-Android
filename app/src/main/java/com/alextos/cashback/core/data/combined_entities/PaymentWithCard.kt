package com.alextos.cashback.core.data.combined_entities

import androidx.room.Embedded
import androidx.room.Relation
import com.alextos.cashback.core.data.entities.CardEntity
import com.alextos.cashback.core.data.entities.PaymentEntity

data class PaymentWithCard(
    @Embedded
    val payment: PaymentEntity,
    @Relation(parentColumn = "cardId", entityColumn = "id")
    val card: CardEntity,
)
