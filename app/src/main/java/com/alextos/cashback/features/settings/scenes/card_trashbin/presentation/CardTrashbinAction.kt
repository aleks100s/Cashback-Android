package com.alextos.cashback.features.settings.scenes.card_trashbin.presentation

import com.alextos.cashback.core.domain.models.Card

sealed interface CardTrashbinAction {
    data class RestoreCard(val card: Card) : CardTrashbinAction
    data object RestoreAll : CardTrashbinAction
}