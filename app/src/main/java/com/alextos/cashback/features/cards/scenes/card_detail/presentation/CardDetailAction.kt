package com.alextos.cashback.features.cards.scenes.card_detail.presentation

import com.alextos.cashback.core.domain.models.Cashback
import com.alextos.cashback.core.domain.models.currency.Currency

sealed interface CardDetailAction {
    data object ToggleEditMode: CardDetailAction
    data class DeleteCashback(val cashback: Cashback): CardDetailAction
    data class EditCashback(val cashback: Cashback): CardDetailAction
    data class ChangeCardName(val name: String): CardDetailAction
    data object ToggleFavourite: CardDetailAction
    data class ChangeCurrency(val currency: Currency): CardDetailAction
    data class ChangeColor(val color: String): CardDetailAction
    data object DeleteAllCashback: CardDetailAction
    data object ShowDeleteCardDialog: CardDetailAction
    data object DismissDeleteCardDialog: CardDetailAction
    data object DeleteCard: CardDetailAction
}