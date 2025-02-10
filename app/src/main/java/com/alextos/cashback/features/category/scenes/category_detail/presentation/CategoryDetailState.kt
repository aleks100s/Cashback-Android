package com.alextos.cashback.features.category.scenes.category_detail.presentation

import com.alextos.cashback.util.UiText

data class CategoryDetailState(
    val categoryName: String = "",
    val emoji: String = "",
    val description: String = "",
    val isValid: Boolean = false,
    val title: UiText = UiText.DynamicString("")
)
