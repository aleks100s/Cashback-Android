package com.alextos.cashback.features.category.presentation.category_list

import com.alextos.cashback.core.domain.models.Category

data class CategoryListState(
    val searchQuery: String = "",
    val allCategories: List<Category> = listOf(),
    val filteredCategories: List<Category> = listOf()
)
