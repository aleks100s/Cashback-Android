package com.alextos.cashback.features.settings.scenes.category_trashbin.domain

import com.alextos.cashback.core.domain.models.Category
import kotlinx.coroutines.flow.Flow

interface CategoryTrashbinRepository {
    fun getArchivedCategories(): Flow<List<Category>>
    suspend fun unarchive(category: Category)
}