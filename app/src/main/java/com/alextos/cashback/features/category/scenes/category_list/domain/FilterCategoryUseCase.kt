package com.alextos.cashback.features.category.scenes.category_list.domain

import com.alextos.cashback.core.domain.models.Category

class FilterCategoryUseCase {
    fun execute(categories: List<Category>, query: String): List<Category> {
        val query = query.lowercase()
        return categories.filter { category ->
            category.name.lowercase().contains(query)
                    || category.synonyms?.lowercase()?.contains(query) ?: true
        }
    }
}