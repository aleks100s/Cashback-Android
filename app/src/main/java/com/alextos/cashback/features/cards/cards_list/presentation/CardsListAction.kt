package com.alextos.cashback.features.cards.cards_list.presentation

import com.alextos.cashback.core.domain.Card

sealed interface CardsListAction {
    data class SearchQueryChange(val query: String): CardsListAction
    data class CardSelect(val card: Card): CardsListAction
}