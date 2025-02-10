package com.alextos.cashback.features.category.scenes.create_category.presentation

data class CreateCategoryState(
    val categoryName: String = "",
    val emoji: String = "",
    val description: String = "",
    val isValid: Boolean = false
)
