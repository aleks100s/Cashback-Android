package com.alextos.cashback.features.category.scenes.category_list.presentation

import com.alextos.cashback.core.domain.models.Category

data class CategoryListState(
    val searchQuery: String = "",
    val allCategories: List<Category> = listOf(),
    val filteredCategories: List<Category> = listOf()
)
