package com.alextos.cashback.features.category.presentation.category_list

sealed interface CategoryListAction {
    data class SearchQueryChanged(val query: String): CategoryListAction
    data class CreateCategory(val name: String): CategoryListAction
}