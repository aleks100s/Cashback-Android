package com.alextos.cashback.features.category.data

import com.alextos.cashback.core.data.dao.CategoryDao
import com.alextos.cashback.core.data.mappers.toDomain
import com.alextos.cashback.core.domain.models.Category
import com.alextos.cashback.features.category.domain.CategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CategoryRepositoryImpl(
    private val categoryDao: CategoryDao
): CategoryRepository {
    override fun getAllCategories(): Flow<List<Category>> {
        return categoryDao.getAll().map { list ->
            list.map { it.toDomain() }
        }
    }
}