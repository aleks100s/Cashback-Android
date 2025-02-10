package com.alextos.cashback.features.cards.presentation.card_detail

import com.alextos.cashback.core.domain.models.Cashback

sealed interface CardDetailAction {
    data object ToggleEditMode: CardDetailAction
    data class ShowDeleteCashbackDialog(val cashback: Cashback): CardDetailAction
    data object DismissDeleteCashbackDialog: CardDetailAction
    data class DeleteCashback(val cashback: Cashback): CardDetailAction
    data class EditCashback(val cashback: Cashback): CardDetailAction
}