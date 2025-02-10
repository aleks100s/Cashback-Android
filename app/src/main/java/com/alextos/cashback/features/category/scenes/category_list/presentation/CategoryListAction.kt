package com.alextos.cashback.features.category.scenes.category_list.presentation

import com.alextos.cashback.core.domain.models.Category

sealed interface CategoryListAction {
    data class SearchQueryChanged(val query: String): CategoryListAction
    data class CreateCategory(val name: String): CategoryListAction
    data class SelectCategory(val category: Category): CategoryListAction
    data class DeleteCategory(val category: Category): CategoryListAction
    data class EditCategory(val category: Category): CategoryListAction
    data object DismissCreateCategorySheet: CategoryListAction
}