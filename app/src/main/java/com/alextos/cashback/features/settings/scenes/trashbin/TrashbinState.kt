package com.alextos.cashback.features.settings.scenes.trashbin

import com.alextos.cashback.core.domain.models.Card
import com.alextos.cashback.core.domain.models.Category

data class TrashbinState(
    val categories: List<Category> = emptyList(),
    val cards: List<Card> = emptyList()
)
