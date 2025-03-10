package com.alextos.cashback.features.settings.scenes.category_trashbin.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alextos.cashback.R
import com.alextos.cashback.common.UiText
import com.alextos.cashback.core.domain.repository.CategoryRepository
import com.alextos.cashback.core.domain.services.AnalyticsEvent
import com.alextos.cashback.core.domain.services.AnalyticsService
import com.alextos.cashback.core.domain.services.ToastService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CategoryTrashbinViewModel(
    private val repository: CategoryRepository,
    private val toastService: ToastService,
    private val analyticsService: AnalyticsService
): ViewModel() {
    private val _state = MutableStateFlow(CategoryTrashbinState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getArchivedCategories()
                .collect { categories ->
                    _state.update { it.copy(categories = categories) }
                }
        }
    }

    fun onAction(action: CategoryTrashbinAction) {
        when (action) {
            is CategoryTrashbinAction.RestoreAll -> {
                analyticsService.logEvent(AnalyticsEvent.TrashbinCategoriesRestoreButtonTapped)
                viewModelScope.launch(Dispatchers.IO) {
                    state.value.categories.forEach {
                        repository.unarchive(it)
                    }
                }
                toastService.showToast(UiText.StringResourceId(R.string.trashbin_categories_restored))
            }
            is CategoryTrashbinAction.RestoreCategory -> {
                analyticsService.logEvent(AnalyticsEvent.TrashbinRestoreCategory)
                viewModelScope.launch(Dispatchers.IO) {
                    repository.unarchive(action.category)
                }
                toastService.showToast(UiText.StringResourceId(R.string.trashbin_category_restored))
            }
        }
    }
}