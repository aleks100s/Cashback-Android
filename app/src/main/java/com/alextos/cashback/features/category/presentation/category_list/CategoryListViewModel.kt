package com.alextos.cashback.features.category.presentation.category_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alextos.cashback.features.category.domain.CategoryRepository
import com.alextos.cashback.features.category.domain.FilterCategoryUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CategoryListViewModel(
    private val filterUseCase: FilterCategoryUseCase,
    private val categoryRepository: CategoryRepository
): ViewModel() {
    private val _state = MutableStateFlow(CategoryListState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            categoryRepository.getAllCategories()
                .collect { list ->
                    _state.update { state ->
                        state.copy(
                            allCategories = list,
                            filteredCategories = list
                        )
                    }
                }
        }
    }

    fun onAction(action: CategoryListAction) {
        when (action) {
            is CategoryListAction.SearchQueryChanged -> {
                val query = action.query
                _state.update { state ->
                    state.copy(
                        searchQuery = query,
                        filteredCategories = filterUseCase.execute(state.allCategories, query)
                    )
                }
            }
            is CategoryListAction.CreateCategory -> {}
        }
    }
}