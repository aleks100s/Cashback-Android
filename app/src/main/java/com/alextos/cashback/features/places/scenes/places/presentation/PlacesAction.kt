package com.alextos.cashback.features.places.scenes.places.presentation

import com.alextos.cashback.core.domain.models.Place

sealed interface PlacesAction {
    data class SearchQueryChanged(val query: String): PlacesAction
    data class PlaceSelected(val place: Place): PlacesAction
    data object AddPlace: PlacesAction
    data class FavouriteToggle(val place: Place): PlacesAction
    data class DeletePlace(val place: Place): PlacesAction
    data class EditPlace(val place: Place): PlacesAction
}