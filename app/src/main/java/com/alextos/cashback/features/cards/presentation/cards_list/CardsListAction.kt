package com.alextos.cashback.features.cards.presentation.cards_list

import com.alextos.cashback.core.domain.models.Card

sealed interface CardsListAction {
    data class SearchQueryChange(val query: String): CardsListAction
    data class CardSelect(val card: Card): CardsListAction
    data class ToggleFavourite(val card: Card): CardsListAction
    data object AddCard: CardsListAction
    data object DismissAddCardSheet: CardsListAction
    data class CardNameChange(val name: String): CardsListAction
    data object SaveButtonTapped: CardsListAction
}