package com.alextos.cashback.core.data.repository

import com.alextos.cashback.core.data.dao.CategoryDao
import com.alextos.cashback.core.data.entities.mappers.toDomain
import com.alextos.cashback.core.data.entities.mappers.toEntity
import com.alextos.cashback.core.domain.models.Category
import com.alextos.cashback.core.domain.repository.CategoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

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

    override fun getPopularCategories(): Flow<List<Category>> {
        return categoryDao.getPopularCategories().map { list ->
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

    override suspend fun getAllCategoriesExport(): List<Category> {
        return categoryDao.getCategoriesExport().map { it.toDomain() }
    }

    override fun getArchivedCategories(): Flow<List<Category>> {
        return categoryDao.getAllArchived()
            .map { list ->
                list.map { it.toDomain() }
            }
    }

    override suspend fun unarchive(category: Category) {
        withContext(Dispatchers.IO) {
            categoryDao.upsert(category.copy(isArchived = false).toEntity())
        }
    }
}