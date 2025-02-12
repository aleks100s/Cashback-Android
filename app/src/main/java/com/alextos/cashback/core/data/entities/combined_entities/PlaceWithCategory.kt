package com.alextos.cashback.core.data.entities.combined_entities

import androidx.room.Embedded
import androidx.room.Relation
import com.alextos.cashback.core.data.entities.CategoryEntity
import com.alextos.cashback.core.data.entities.PlaceEntity

data class PlaceWithCategory(
    @Embedded
    val place: PlaceEntity,
    @Relation(parentColumn = "categoryId", entityColumn = "id")
    val category: CategoryEntity
)
