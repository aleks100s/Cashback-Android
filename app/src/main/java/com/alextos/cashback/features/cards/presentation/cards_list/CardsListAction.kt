package com.alextos.cashback.features.cards.presentation.cards_list

import com.alextos.cashback.core.domain.Card

sealed interface CardsListAction {
    data class SearchQueryChange(val query: String): CardsListAction
    data class CardSelect(val card: Card): CardsListAction
}