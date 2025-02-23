package com.alextos.cashback.features.settings.scenes.category_trashbin.data

import com.alextos.cashback.core.data.dao.CategoryDao
import com.alextos.cashback.core.data.entities.mappers.toDomain
import com.alextos.cashback.core.data.entities.mappers.toEntity
import com.alextos.cashback.core.domain.models.Category
import com.alextos.cashback.features.settings.scenes.category_trashbin.domain.CategoryTrashbinRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class CategoryTrashbinRepositoryImpl(
    private val categoryDao: CategoryDao
): CategoryTrashbinRepository {
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