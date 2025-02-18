package com.alextos.cashback.features.category.scenes.category_list.domain

import com.alextos.cashback.core.domain.models.Category
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FilterCategoryUseCase {
    suspend fun execute(categories: List<Category>, query: String): List<Category> {
        return withContext(Dispatchers.IO) {
            val query = query.lowercase()
            return@withContext categories.filter { category ->
                category.name.lowercase().contains(query)
                        || category.synonyms?.lowercase()?.contains(query) ?: false
            }
        }
    }
}