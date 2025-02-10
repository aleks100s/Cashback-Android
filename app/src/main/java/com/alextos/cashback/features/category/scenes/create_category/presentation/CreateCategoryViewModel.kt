package com.alextos.cashback.features.category.scenes.create_category.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alextos.cashback.core.domain.models.Category
import com.alextos.cashback.features.category.scenes.category_list.domain.CategoryRepository
import com.alextos.cashback.features.category.scenes.create_category.domain.ValidateCategoryUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CreateCategoryViewModel(
    private val repository: CategoryRepository,
    private val validateCategoryUseCase: ValidateCategoryUseCase
): ViewModel() {
    private val _state = MutableStateFlow(CreateCategoryState())
    val state = _state.asStateFlow()

    init {
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

    fun onAction(action: CreateCategoryAction) {
        when (action) {
            is CreateCategoryAction.ChangeCategoryName -> {
                _state.update {
                    it.copy(categoryName = action.name)
                }
            }
            is CreateCategoryAction.ChangeCategoryEmoji -> {
                _state.update {
                    it.copy(emoji = action.emoji)
                }
            }
            is CreateCategoryAction.ChangeCategoryDescription -> {
                _state.update {
                    it.copy(description = action.description)
                }
            }
            is CreateCategoryAction.SaveButtonTapped -> {
                val state = state.value
                viewModelScope.launch(Dispatchers.IO) {
                    val category = Category(name = state.categoryName, emoji = state.emoji, info = state.description, isNative = false)
                    repository.createOrUpdate(category)
                }
            }
        }
    }
}