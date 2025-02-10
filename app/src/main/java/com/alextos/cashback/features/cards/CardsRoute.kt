package com.alextos.cashback.features.cards

import kotlinx.serialization.Serializable

sealed interface CardsRoute {
    @Serializable
    data object CardsGraph: CardsRoute

    @Serializable
    data object CardsList: CardsRoute

    @Serializable
    data class CardDetail(val cardId: String)

    @Serializable
    data class CashbackDetail(val cardId: String, val cashbackId: String?)

    @Serializable
    data object SelectCategory
}