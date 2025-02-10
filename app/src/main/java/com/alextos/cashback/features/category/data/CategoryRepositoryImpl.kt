package com.alextos.cashback.features.category.data

import com.alextos.cashback.core.data.dao.CategoryDao
import com.alextos.cashback.core.data.mappers.toDomain
import com.alextos.cashback.core.data.mappers.toEntity
import com.alextos.cashback.core.domain.models.Category
import com.alextos.cashback.features.category.scenes.category_list.domain.CategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CategoryRepositoryImpl(
    private val categoryDao: CategoryDao
): CategoryRepository {
    override fun getUnarchivedCategories(): Flow<List<Category>> {
        return categoryDao.getAllUnarchived()
            .map { list ->
                list.map { it.toDomain() }
            }
    }

    override fun getAllCategories(): Flow<List<Category>> {
        return categoryDao.getAll().map { list ->
            list.map { it.toDomain() }
        }
    }

    override suspend fun getCategory(id: String): Category? {
        return categoryDao
            .getCategory(id)
            .firstOrNull()?.toDomain()
    }

    override suspend fun createOrUpdate(category: Category) {
        categoryDao.upsert(category.toEntity())
    }
}