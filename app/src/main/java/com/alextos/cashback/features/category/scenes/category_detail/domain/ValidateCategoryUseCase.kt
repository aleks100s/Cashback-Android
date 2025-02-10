package com.alextos.cashback.features.category.scenes.category_detail.domain

class ValidateCategoryUseCase {
    fun execute(name: String, emoji: String, description: String): Boolean {
        return name.isNotEmpty()
    }
}