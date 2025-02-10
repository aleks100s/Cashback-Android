package com.alextos.cashback.features.category.domain

import com.alextos.cashback.core.domain.models.Category

class ArchiveCategoryUseCase(
    private val repository: CategoryRepository
) {
    suspend fun execute(category: Category) {
        category.isArchived = true
        repository.createOrUpdate(category)
    }
}