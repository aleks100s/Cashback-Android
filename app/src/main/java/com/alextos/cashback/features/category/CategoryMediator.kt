package com.alextos.cashback.features.category

import com.alextos.cashback.core.domain.models.Category
import kotlinx.coroutines.flow.StateFlow

interface CategoryMediator {
    val selectedCategory: StateFlow<Category?>

    suspend fun setSelectedCategory(category: Category)
}