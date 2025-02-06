package com.alextos.cashback.features.cards.presentation.card_detail

sealed interface CardDetailAction {
    data object ToggleEditMode: CardDetailAction
    data object AddCashback: CardDetailAction
}