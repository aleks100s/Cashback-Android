package com.alextos.cashback.features.category

import com.alextos.cashback.core.domain.models.Category
import kotlinx.coroutines.flow.SharedFlow

interface CategoryMediator {
    val selectedCategory: SharedFlow<Category>

    suspend fun setSelectedCategory(category: Category)
}