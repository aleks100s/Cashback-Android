package com.alextos.cashback.features.category.scenes.category_list.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alextos.cashback.R
import com.alextos.cashback.common.views.Screen
import com.alextos.cashback.common.views.ContextMenuItem
import com.alextos.cashback.common.views.RoundedList
import com.alextos.cashback.common.views.SearchBar
import com.alextos.cashback.core.presentation.views.CategoryItemView
import com.alextos.cashback.common.UiText
import com.alextos.cashback.common.views.CustomLabel
import com.alextos.cashback.common.views.EmptySearchView

@Composable
fun CategoryListScreen(
    modifier: Modifier = Modifier,
    viewModel: CategoryListViewModel,
    disableSelection: Boolean = false,
    disableBackButton: Boolean = false,
    goBack: () -> Unit,
    onSelectCategory: () -> Unit,
    onCreateCategory: (String) -> Unit,
    onEditCategory: (String) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val haptic = LocalHapticFeedback.current

    Screen(
        modifier = modifier,
        title = stringResource(R.string.category_list_title),
        goBack = if (disableBackButton) null else goBack,
        floatingActionButton = {
            Button(onClick = {
                onCreateCategory(state.searchQuery)
            }) {
                CustomLabel(
                    title = stringResource(R.string.category_list_create_category),
                    imageVector = Icons.Filled.Add
                )
            }
        }
    ) {
        CategoryListView(modifier = it, state = state) { action ->
            when (action) {
                is CategoryListAction.SelectCategory -> {
                    if (!disableSelection) {
                        viewModel.onAction(action)
                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                        onSelectCategory()
                    }
                }
                is CategoryListAction.CreateCategory -> {
                    onCreateCategory(action.name)
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                }
                is CategoryListAction.EditCategory -> {
                    onEditCategory(action.category.id)
                }
                is CategoryListAction.DeleteCategory -> {
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                    viewModel.onAction(action)
                }
                else -> {
                    viewModel.onAction(action)
                }
            }
        }
    }
}

@Composable
private fun CategoryListView(
    modifier: Modifier = Modifier,
    state: CategoryListState,
    onAction: (CategoryListAction) -> Unit
) {
    RoundedList(
        modifier = modifier.padding(horizontal = 16.dp),
        list = state.filteredCategories,
        itemView = { itemModifier, category ->
            CategoryItemView(modifier = itemModifier, category = category) {
                onAction(CategoryListAction.InfoButtonTapped)
            }
        },
        stickyHeader = {
            SearchBar(
                value = state.searchQuery,
                placeholder = stringResource(R.string.category_list_placeholder)
            ) {
                onAction(CategoryListAction.SearchQueryChanged(it))
            }
        },
        emptyView = {
            EmptySearchView(
                title = stringResource(R.string.category_list_no_search_results),
                button = {
                    Button(onClick = {
                        onAction(CategoryListAction.CreateCategory(name = state.searchQuery))
                    }) {
                        Text(text = stringResource(R.string.category_list_add_new_category))
                    }
                }
            )
        },
        onItemClick = {
            onAction(CategoryListAction.SelectCategory(it))
        },
        contextMenuActions = { category ->
            if (category.isNative) {
                listOf(
                    ContextMenuItem(
                        title = UiText.StringResourceId(R.string.category_list_delete_category),
                        isDestructive = true,
                        action = {
                            onAction(CategoryListAction.DeleteCategory(it))
                        }
                    )
                )
            } else {
                listOf(
                    ContextMenuItem(
                        title = UiText.StringResourceId(R.string.category_list_edit_category),
                        action = {
                            onAction(CategoryListAction.EditCategory(it))
                        }
                    ),
                    ContextMenuItem(
                        title = UiText.StringResourceId(R.string.category_list_delete_category),
                        isDestructive = true,
                        action = {
                            onAction(CategoryListAction.DeleteCategory(it))
                        }
                    )
                )
            }
        },
        allowSwipe = false,
    )
}