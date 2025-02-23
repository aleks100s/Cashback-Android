package com.alextos.cashback.core.data.entities.mappers

import com.alextos.cashback.core.data.entities.combined_entities.PlaceWithCategory
import com.alextos.cashback.core.data.entities.PlaceEntity
import com.alextos.cashback.core.domain.models.Place
import java.util.UUID

fun Place.toEntity(): PlaceEntity {
    return PlaceEntity(
        id = id,
        name = name,
        categoryId = category.id,
        isFavourite = isFavourite
    )
}

fun PlaceWithCategory.toDomain(): Place {
    return Place(
        id = place.id,
        name = place.name,
        category = category.toDomain(),
        isFavourite = place.isFavourite
    )
}