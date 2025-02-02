package com.alextos.cashback.features.cards

import kotlinx.serialization.Serializable

sealed interface CardsRoute {
    @Serializable
    data object CardsGraph: CardsRoute

    @Serializable
    data object CardsList: CardsRoute

    @Serializable
    data class CardDetail(val cardId: String)
}