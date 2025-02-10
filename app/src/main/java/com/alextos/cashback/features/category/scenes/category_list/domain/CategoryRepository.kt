package com.alextos.cashback.features.category.scenes.category_list.domain

import com.alextos.cashback.core.domain.models.Category
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    fun getUnarchivedCategories(): Flow<List<Category>>
    fun getAllCategories(): Flow<List<Category>>
    suspend fun createOrUpdate(category: Category)
}