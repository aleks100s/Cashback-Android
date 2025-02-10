package com.alextos.cashback.features.category.scenes.create_category.presentation

sealed interface CreateCategoryAction {
    data class ChangeCategoryName(val name: String): CreateCategoryAction
    data class ChangeCategoryEmoji(val emoji: String): CreateCategoryAction
    data class ChangeCategoryDescription(val description: String): CreateCategoryAction
    data object SaveButtonTapped: CreateCategoryAction
}