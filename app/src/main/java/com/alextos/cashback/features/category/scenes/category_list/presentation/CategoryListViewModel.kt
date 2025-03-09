package com.alextos.cashback.features.category.scenes.category_list.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alextos.cashback.R
import com.alextos.cashback.core.domain.services.ToastService
import com.alextos.cashback.features.category.CategoryMediator
import com.alextos.cashback.features.category.scenes.category_list.domain.ArchiveCategoryUseCase
import com.alextos.cashback.core.domain.repository.CategoryRepository
import com.alextos.cashback.features.category.scenes.category_list.domain.FilterCategoryUseCase
import com.alextos.cashback.features.category.scenes.category_list.domain.IncreaseCategoryPriorityUseCase
import com.alextos.cashback.common.UiText
import com.alextos.cashback.core.domain.services.AnalyticsEvent
import com.alextos.cashback.core.domain.services.AnalyticsService
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
    private val toastService: ToastService,
    private val analyticsService: AnalyticsService
): ViewModel() {
    private val _state = MutableStateFlow(CategoryListState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            categoryRepository.getUnarchivedCategories()
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
                _state.update { it.copy(searchQuery = query) }
                viewModelScope.launch(Dispatchers.IO) {
                    _state.update { state ->
                        state.copy(filteredCategories = filterUseCase.execute(state.allCategories, query))
                    }
                }
            }
            is CategoryListAction.SelectCategory -> {
                analyticsService.logEvent(AnalyticsEvent.SelectCategorySelect)
                viewModelScope.launch(Dispatchers.IO) {
                    categoryMediator.setSelectedCategory(action.category)
                    increaseCategoryPriorityUseCase.execute(action.category)
                }
            }
            is CategoryListAction.DeleteCategory -> {
                analyticsService.logEvent(AnalyticsEvent.SelectCategoryDeleteButtonTapped)
                viewModelScope.launch(Dispatchers.IO) {
                    archiveCategoryUseCase.execute(action.category)
                }
                toastService.showToast(UiText.StringResourceId(R.string.category_list_category_removed))
            }
            is CategoryListAction.CreateCategory -> {
                analyticsService.logEvent(AnalyticsEvent.SelectCategoryCreateNewButtonTapped)
            }
            is CategoryListAction.EditCategory -> {
                analyticsService.logEvent(AnalyticsEvent.SelectCategoryEditButtonTapped)
            }
            is CategoryListAction.InfoButtonTapped -> {
                analyticsService.logEvent(AnalyticsEvent.SelectCategoryInfoButtonTapped)
            }
        }
    }
}