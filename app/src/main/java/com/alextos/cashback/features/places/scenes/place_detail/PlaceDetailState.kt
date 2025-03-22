package com.alextos.cashback.features.places.scenes.place_detail

import com.alextos.cashback.core.domain.models.Category

data class PlaceDetailState(
    val placeName: String = "",
    val category: Category? = null,
    val isFavourite: Boolean = false,
    val isAdVisible: Boolean = false,
    val isEditMode: Boolean = false
)
