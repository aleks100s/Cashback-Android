package com.alextos.cashback.core.data.dto.mappers

import com.alextos.cashback.core.data.dto.PlaceDto
import com.alextos.cashback.core.domain.models.Place

fun Place.toDto(): PlaceDto {
    return PlaceDto(
        id = id,
        name = name,
        category = category.toDto(),
        isFavourite = isFavourite
    )
}

fun PlaceDto.toDomain(): Place {
    return Place(
        id = id,
        name = name,
        category = category.toDomain(),
        isFavourite = isFavourite
    )
}