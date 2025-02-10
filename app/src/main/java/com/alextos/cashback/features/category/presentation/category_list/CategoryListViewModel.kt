package com.alextos.cashback.features.category.presentation.category_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alextos.cashback.R
import com.alextos.cashback.core.domain.services.ToastService
import com.alextos.cashback.features.category.CategoryMediator
import com.alextos.cashback.features.category.domain.ArchiveCategoryUseCase
import com.alextos.cashback.features.category.domain.CategoryRepository
import com.alextos.cashback.features.category.domain.FilterCategoryUseCase
import com.alextos.cashback.features.category.domain.IncreaseCategoryPriorityUseCase
import com.alextos.cashback.util.UiText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CategoryListViewModel(
    private val filterUseCase: FilterCategoryUseCase,
    private val increaseCategoryPriorityUseCase: IncreaseCategoryPriorityUseCase,
    private val archiveCategoryUseCase: ArchiveCategoryUseCase,
    private val categoryRepository: CategoryRepository,
    private val categoryMediator: CategoryMediator,
    private val toastService: ToastService
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
            is CategoryListAction.SelectCategory -> {
                viewModelScope.launch(Dispatchers.IO) {
                    categoryMediator.setSelectedCategory(action.category)
                    increaseCategoryPriorityUseCase.execute(action.category)
                }
            }
            is CategoryListAction.CreateCategory -> {}
            is CategoryListAction.DeleteCategory -> {
                viewModelScope.launch(Dispatchers.IO) {
                    archiveCategoryUseCase.execute(action.category)
                    viewModelScope.launch(Dispatchers.Main) {
                        toastService.showToast(UiText.StringResourceId(R.string.category_list_category_removed))
                    }
                }
            }
            is CategoryListAction.EditCategory -> {}
        }
    }
}