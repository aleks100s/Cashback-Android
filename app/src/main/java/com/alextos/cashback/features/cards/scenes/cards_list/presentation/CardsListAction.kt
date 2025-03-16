package com.alextos.cashback.features.cards.scenes.cards_list.presentation

import com.alextos.cashback.core.domain.models.Card
import com.alextos.cashback.core.domain.models.Category
import com.alextos.cashback.core.domain.models.currency.Currency

sealed interface CardsListAction {
    data class SearchQueryChange(val query: String): CardsListAction
    data class CardSelect(val card: Card): CardsListAction
    data class ToggleFavourite(val card: Card): CardsListAction
    data object AddCard: CardsListAction
    data object DismissAddCardSheet: CardsListAction
    data class CardNameChange(val name: String): CardsListAction
    data class CardColorChange(val color: String): CardsListAction
    data class CardCurrencyChange(val currency: Currency): CardsListAction
    data object SaveButtonTapped: CardsListAction
    data class SelectCategory(val category: Category): CardsListAction
    data object EditButtonTapped: CardsListAction
    data class CompactViewToggle(val isActive: Boolean) : CardsListAction
    data object DismissSettingsSheet : CardsListAction
}