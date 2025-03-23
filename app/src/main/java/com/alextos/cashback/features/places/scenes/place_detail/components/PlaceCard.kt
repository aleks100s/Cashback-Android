package com.alextos.cashback.features.places.scenes.place_detail.components

import com.alextos.cashback.core.domain.models.Card
import com.alextos.cashback.core.domain.models.Cashback

data class PlaceCard(
    val card: Card,
    val cashback: Cashback
)
