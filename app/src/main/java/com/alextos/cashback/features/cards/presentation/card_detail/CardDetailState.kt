package com.alextos.cashback.features.cards.presentation.card_detail

import com.alextos.cashback.core.domain.models.Card

data class CardDetailState(
    val isEditMode: Boolean = false,
    val card: Card? = null,
    val cardName: String = "",
    val isFavourite: Boolean = false,
    val currency: String = "",
    val isDeleteCardDialogShown: Boolean = false
)
