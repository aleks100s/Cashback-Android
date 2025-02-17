package com.alextos.cashback.core.domain.repository

import com.alextos.cashback.core.domain.models.Category
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    fun getUnarchivedCategories(): Flow<List<Category>>
    fun getAllCategories(): Flow<List<Category>>
    fun getPopularCategories(): Flow<List<Category>>
    suspend fun getCategory(id: String): Category?
    suspend fun createOrUpdate(category: Category)
}