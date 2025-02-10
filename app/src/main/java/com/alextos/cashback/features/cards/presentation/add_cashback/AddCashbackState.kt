package com.alextos.cashback.features.cards.presentation.add_cashback

import com.alextos.cashback.core.domain.models.Card
import com.alextos.cashback.core.domain.models.Category
import com.alextos.cashback.util.UiText

data class AddCashbackState(
    val percent: String = "5",
    val selectedCategory: Category? = null,
    val percentOptions: List<Int> = listOf(1, 3, 5, 10),
    val card: Card? = null,
    val isValid: Boolean = false,
    val title: UiText = UiText.DynamicString("")
)
