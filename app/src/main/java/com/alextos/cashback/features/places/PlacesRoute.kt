package com.alextos.cashback.features.places

import kotlinx.serialization.Serializable

sealed interface PlacesRoute {
    @Serializable
    data object PlacesGraph: PlacesRoute

    @Serializable
    data object Places: PlacesRoute

    @Serializable
    data class PlaceDetails(val placeId: String): PlacesRoute

    @Serializable
    data object SelectCategory: PlacesRoute
}