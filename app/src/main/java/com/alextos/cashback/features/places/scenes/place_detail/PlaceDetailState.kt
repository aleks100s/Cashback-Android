package com.alextos.cashback.features.places.scenes.place_detail

import com.alextos.cashback.core.domain.models.Card
import com.alextos.cashback.core.domain.models.Category
import com.alextos.cashback.features.places.scenes.place_detail.components.PlaceCard

data class PlaceDetailState(
    val placeName: String = "",
    val category: Category? = null,
    val isFavourite: Boolean = false,
    val isEditMode: Boolean = false,
    val isCreateMode: Boolean = false,
    val cards: List<PlaceCard> = emptyList()
)
