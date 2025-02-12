package com.alextos.cashback.core.data.entities.combined_entities

import androidx.room.Embedded
import androidx.room.Relation
import com.alextos.cashback.core.data.entities.CashbackEntity
import com.alextos.cashback.core.data.entities.CategoryEntity

data class CashbackWithCategory(
    @Embedded
    val cashbackEntity: CashbackEntity,
    @Relation(parentColumn = "categoryId", entityColumn = "id")
    val categoryEntity: CategoryEntity
)
