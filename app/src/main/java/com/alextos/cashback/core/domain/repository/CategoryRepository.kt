package com.alextos.cashback.core.domain.repository

import com.alextos.cashback.core.domain.models.Category
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    fun getUnarchivedCategories(): Flow<List<Category>>
    fun getAllCategoriesFlow(): Flow<List<Category>>
    fun getPopularCategories(): Flow<List<Category>>
    suspend fun getCategory(id: String): Category?
    suspend fun createOrUpdate(category: Category)
    suspend fun getAllCategories(): List<Category>
    fun getArchivedCategories(): Flow<List<Category>>
    suspend fun unarchive(category: Category)
    suspend fun replaceAll(categories: List<Category>)
}