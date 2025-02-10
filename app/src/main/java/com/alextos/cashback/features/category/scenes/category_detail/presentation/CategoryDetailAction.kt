package com.alextos.cashback.features.category.scenes.category_detail.presentation

sealed interface CategoryDetailAction {
    data class ChangeCategoryDetailName(val name: String): CategoryDetailAction
    data class ChangeCategoryDetailEmoji(val emoji: String): CategoryDetailAction
    data class ChangeCategoryDetailDescription(val description: String): CategoryDetailAction
    data object SaveButtonTapped: CategoryDetailAction
}