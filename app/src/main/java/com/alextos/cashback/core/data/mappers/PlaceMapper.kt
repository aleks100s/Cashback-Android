package com.alextos.cashback.core.data.mappers

import com.alextos.cashback.core.data.combined_entities.PlaceWithCategory
import com.alextos.cashback.core.data.entities.PlaceEntity
import com.alextos.cashback.core.domain.Place
import java.util.UUID

fun Place.toEntity(): PlaceEntity {
    return PlaceEntity(
        id = id.toString(),
        name = name,
        categoryId = category.id.toString(),
        isFavourite = isFavourite
    )
}

fun PlaceWithCategory.toDomain(): Place {
    return Place(
        id = UUID.fromString(place.id),
        name = place.name,
        category = category.toDomain(),
        isFavourite = place.isFavourite
    )
}