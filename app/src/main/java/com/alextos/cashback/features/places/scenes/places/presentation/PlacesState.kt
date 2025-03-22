package com.alextos.cashback.features.places.scenes.places.presentation

import com.alextos.cashback.core.domain.models.Place

data class PlacesState(
    val allPlaces: List<Place> = emptyList(),
    val filteredPlaces: List<Place> = emptyList(),
    val isLoading: Boolean = false,
    val searchQuery: String = "",
)
