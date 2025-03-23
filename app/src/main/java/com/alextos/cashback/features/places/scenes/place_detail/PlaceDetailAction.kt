package com.alextos.cashback.features.places.scenes.place_detail

import com.alextos.cashback.core.domain.models.Category

interface PlaceDetailAction {
    data object ToggleEditMode: PlaceDetailAction
    data object ToggleFavourite: PlaceDetailAction
    data class ChangeName(val name: String): PlaceDetailAction
    data object SelectCategory: PlaceDetailAction
    data class CategorySelected(val category: Category): PlaceDetailAction
    data object SavePlace: PlaceDetailAction
    data object DeletePlace: PlaceDetailAction
}