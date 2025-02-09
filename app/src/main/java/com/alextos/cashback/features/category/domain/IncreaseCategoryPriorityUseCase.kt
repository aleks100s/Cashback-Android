package com.alextos.cashback.features.category.domain

import com.alextos.cashback.core.domain.models.Category

class IncreaseCategoryPriorityUseCase(
    private val repository: CategoryRepository
) {
    suspend fun execute(category: Category) {
        category.priority += 1
        repository.createOrUpdate(category)
    }
}