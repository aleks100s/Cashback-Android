package com.alextos.cashback.features.settings.scenes.card_trashbin.presentation

import com.alextos.cashback.core.domain.models.Card

data class CardTrashbinState(
    val cards: List<Card> = emptyList()
)
