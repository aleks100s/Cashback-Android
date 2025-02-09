package com.alextos.cashback.features.category

import com.alextos.cashback.core.domain.models.Category
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class CategoryMediatorImpl: CategoryMediator {
    private val _selectedCategory = MutableStateFlow<Category?>(null)
    override val selectedCategory = _selectedCategory.asStateFlow()

    override suspend fun setSelectedCategory(category: Category) {
        _selectedCategory.value = category
    }
}