package com.alextos.cashback.features.category.domain

import com.alextos.cashback.core.domain.models.Category
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    fun getAllCategories(): Flow<List<Category>>
    suspend fun createOrUpdate(category: Category)
}