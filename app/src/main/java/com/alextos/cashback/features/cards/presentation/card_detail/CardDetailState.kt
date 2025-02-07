package com.alextos.cashback.features.cards.presentation.card_detail

import com.alextos.cashback.core.domain.models.Card
import com.alextos.cashback.core.domain.models.Cashback

data class CardDetailState(
    val isEditMode: Boolean = false,
    val card: Card? = null,
    val cashbackToDelete: Cashback? = null
)
