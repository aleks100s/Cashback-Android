package com.alextos.cashback.features.cards.scenes.card_detail.presentation

import com.alextos.cashback.core.domain.models.Card

data class CardDetailState(
    val isEditMode: Boolean = false,
    val card: Card? = null,
    val cardName: String = "",
    val color: String = "#E7E7E7",
    val isFavourite: Boolean = false,
    val currency: String = "",
    val isDeleteCardDialogShown: Boolean = false,
    val isAdVisible: Boolean = true
)
