package com.alextos.cashback.core.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class PlaceDto(
    val id: String,
    val name: String,
    val category: CategoryDto,
    val isFavorite: Boolean = false
)
