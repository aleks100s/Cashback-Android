package com.alextos.cashback.features.category

import com.alextos.cashback.core.domain.models.Category
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class CategoryMediatorImpl: CategoryMediator {
    private val _selectedCategory = MutableSharedFlow<Category>()
    override val selectedCategory = _selectedCategory.asSharedFlow()

    override suspend fun setSelectedCategory(category: Category) {
        _selectedCategory.emit(category)
    }
}