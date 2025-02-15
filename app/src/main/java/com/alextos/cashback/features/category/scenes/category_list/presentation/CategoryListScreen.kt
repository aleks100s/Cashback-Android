package com.alextos.cashback.features.category.scenes.category_list.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alextos.cashback.R
import com.alextos.cashback.core.domain.models.generateMockCategory
import com.alextos.cashback.common.views.Screen
import com.alextos.cashback.common.views.ContextMenuItem
import com.alextos.cashback.common.views.RoundedList
import com.alextos.cashback.common.views.SearchBar
import com.alextos.cashback.core.presentation.views.CategoryItemView
import com.alextos.cashback.common.UiText

@Composable
fun CategoryListScreen(
    modifier: Modifier = Modifier,
    viewModel: CategoryListViewModel,
    disableSelection: Boolean = false,
    goBack: () -> Unit,
    onSelectCategory: () -> Unit,
    onCreateCategory: (String) -> Unit,
    onEditCategory: (String) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Screen(
        modifier = modifier,
        title = stringResource(R.string.category_list_title),
        goBack = goBack,
        backButtonIcon = Icons.Default.Close,
        floatingActionButton = {
            FloatingActionButton(onClick = {
                onCreateCategory(state.searchQuery)
            }) {
                Icon(Icons.Filled.Add, stringResource(R.string.category_list_create_category))
            }
        }
    ) {
        CategoryListView(modifier = it, state = state) { action ->
            when (action) {
                is CategoryListAction.SelectCategory -> {
                    if (!disableSelection) {
                        viewModel.onAction(action)
                        onSelectCategory()
                    }
                }
                is CategoryListAction.CreateCategory -> {
                    onCreateCategory(action.name)
                }
                is CategoryListAction.EditCategory -> {
                    onEditCategory(action.category.id)
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
        itemView = { modifier, category ->
            CategoryItemView(modifier = modifier, category = category)
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
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Text(
                    text = stringResource(R.string.category_list_no_search_results),
                    textAlign = TextAlign.Center
                )

                Button(onClick = {
                    onAction(CategoryListAction.CreateCategory(name = state.searchQuery))
                }) {
                    Text(text = stringResource(R.string.category_list_add_new_category))
                }
            }
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

@Preview
@Composable
private fun CategoryListPreview() {
    CategoryListView(state = CategoryListState(filteredCategories = listOf(generateMockCategory(), generateMockCategory(), generateMockCategory()))) { }
}