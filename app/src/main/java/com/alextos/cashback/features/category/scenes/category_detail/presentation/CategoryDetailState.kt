package com.alextos.cashback.features.category.scenes.category_detail.presentation

data class CategoryDetailState(
    val categoryName: String = "",
    val emoji: String = "",
    val description: String = "",
    val isValid: Boolean = false
)
