package com.alextos.cashback.features.places.scenes.places.domain

import com.alextos.cashback.core.domain.models.Place

class FilterPlacesUseCase {
    fun execute(places: List<Place>, query: String): List<Place> {
        return places.filter { it.name.contains(query, ignoreCase = true) }
    }
}