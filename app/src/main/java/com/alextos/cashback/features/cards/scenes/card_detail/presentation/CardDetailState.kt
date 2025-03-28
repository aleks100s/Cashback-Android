package com.alextos.cashback.features.cards.scenes.card_detail.presentation

import com.alextos.cashback.core.domain.models.Card
import com.alextos.cashback.core.domain.models.currency.Currency

data class CardDetailState(
    val isEditMode: Boolean = false,
    val card: Card? = null,
    val cardName: String = "",
    val color: String = "#E7E7E7",
    val isFavourite: Boolean = false,
    val currency: Currency = Currency.RUBLE,
    val isDeleteCardDialogShown: Boolean = false
)
