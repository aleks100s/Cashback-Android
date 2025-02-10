package com.alextos.cashback.features.category.scenes.category_detail.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.alextos.cashback.core.domain.models.Category
import com.alextos.cashback.features.category.CategoryRoute
import com.alextos.cashback.features.category.scenes.category_list.domain.CategoryRepository
import com.alextos.cashback.features.category.scenes.category_detail.domain.ValidateCategoryUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CategoryDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val repository: CategoryRepository,
    private val validateCategoryUseCase: ValidateCategoryUseCase
): ViewModel() {
    private val categoryId = savedStateHandle.toRoute<CategoryRoute.CreateCategory>().categoryId
    private val name = savedStateHandle.toRoute<CategoryRoute.CreateCategory>().name

    private val _state = MutableStateFlow(CategoryDetailState())
    val state = _state.asStateFlow()

    private var category: Category? = null

    init {
        if (name != null) {
            onAction(CategoryDetailAction.ChangeCategoryDetailName(name))
        } else if (categoryId != null) {
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
                val state = state.value
                viewModelScope.launch(Dispatchers.IO) {
                    val emoji = state.emoji.firstOrNull() ?: state.categoryName.firstOrNull() ?: "?"
                    val category = this@CategoryDetailViewModel.category?.copy(
                        name = state.categoryName,
                        emoji = emoji.toString(),
                        info = state.description
                    ) ?: Category(
                        name = state.categoryName,
                        emoji = emoji.toString(),
                        info = state.description,
                        isNative = false
                    )
                    repository.createOrUpdate(category)
                }
            }
        }
    }
}