package com.alextos.cashback.features.category.scenes.category_detail.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.alextos.cashback.R
import com.alextos.cashback.core.domain.models.Category
import com.alextos.cashback.core.domain.services.ToastService
import com.alextos.cashback.features.category.CategoryRoute
import com.alextos.cashback.core.domain.repository.CategoryRepository
import com.alextos.cashback.features.category.scenes.category_detail.domain.ValidateCategoryUseCase
import com.alextos.cashback.common.UiText
import com.alextos.cashback.core.domain.services.AnalyticsEvent
import com.alextos.cashback.core.domain.services.AnalyticsService
import com.alextos.cashback.features.category.scenes.category_detail.domain.CreateOrUpdateCategoryUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CategoryDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val repository: CategoryRepository,
    private val validateCategoryUseCase: ValidateCategoryUseCase,
    private val createOrUpdateCategoryUseCase: CreateOrUpdateCategoryUseCase,
    private val toastService: ToastService,
    private var analyticsService: AnalyticsService
): ViewModel() {
    private val categoryId = savedStateHandle.toRoute<CategoryRoute.CategoryDetail>().categoryId
    private val name = savedStateHandle.toRoute<CategoryRoute.CategoryDetail>().name

    private val _state = MutableStateFlow(CategoryDetailState())
    val state = _state.asStateFlow()

    private var category: Category? = null

    init {
        if (name != null) {
            _state.update { it.copy(title = UiText.StringResourceId(R.string.category_detail_title)) }
            onAction(CategoryDetailAction.ChangeCategoryDetailName(name))
        } else if (categoryId != null) {
            _state.update { it.copy(title = UiText.StringResourceId(R.string.category_detail_edit_title)) }
            viewModelScope.launch(Dispatchers.IO) {
               repository.getCategory(categoryId)?.let { category ->
                   _state.update { state ->
                       state.copy(categoryName = category.name, emoji = category.emoji, description = category.info ?: "")
                   }
                   this@CategoryDetailViewModel.category = category
               }
           }
        }

        viewModelScope.launch(Dispatchers.IO) {
            _state
                .distinctUntilChanged { old, new ->
                    old.categoryName == new.categoryName && old.emoji == new.emoji && old.description == new.description
                }
                .map {
                    validateCategoryUseCase.execute(it.categoryName, it.emoji, it.description)
                }
                .collect { isValid ->
                    _state.update {
                        it.copy(isValid = isValid)
                    }
                }
        }
    }

    fun onAction(action: CategoryDetailAction) {
        when (action) {
            is CategoryDetailAction.ChangeCategoryDetailName -> {
                _state.update {
                    it.copy(categoryName = action.name)
                }
            }
            is CategoryDetailAction.ChangeCategoryDetailEmoji -> {
                _state.update {
                    it.copy(emoji = action.emoji)
                }
            }
            is CategoryDetailAction.ChangeCategoryDetailDescription -> {
                _state.update {
                    it.copy(description = action.description)
                }
            }
            is CategoryDetailAction.SaveButtonTapped -> {
                analyticsService.logEvent(AnalyticsEvent.CreateCategorySaveButtonTapped)
                val state = state.value
                viewModelScope.launch(Dispatchers.IO) {
                    createOrUpdateCategoryUseCase.execute(
                        category = category,
                        categoryName = state.categoryName,
                        emoji = state.emoji,
                        description = state.description
                    )
                    withContext(Dispatchers.Main) {
                        if (categoryId != null) {
                            toastService.showToast(UiText.StringResourceId(R.string.category_detail_changeded))
                        } else {
                            toastService.showToast(UiText.StringResourceId(R.string.category_detail_added))
                        }
                    }
                }
            }
        }
    }
}