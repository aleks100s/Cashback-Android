package com.alextos.cashback.features.category.scenes.category_list.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alextos.cashback.R
import com.alextos.cashback.core.domain.models.generateMockCategory
import com.alextos.cashback.util.views.Screen
import com.alextos.cashback.util.views.ContextMenuItem
import com.alextos.cashback.util.views.RoundedList
import com.alextos.cashback.util.views.SearchBar
import com.alextos.cashback.features.category.scenes.category_list.presentation.components.CategoryItemView
import com.alextos.cashback.features.category.scenes.create_category.presentation.CreateCategoryScreen
import com.alextos.cashback.util.views.CustomWideButton
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryListScreen(
    modifier: Modifier = Modifier,
    viewModel: CategoryListViewModel,
    onSelectCategory: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Screen(
        modifier = modifier,
        title = stringResource(R.string.category_list_title),
        floatingActionButton = {
            FloatingActionButton(onClick = {
                viewModel.onAction(CategoryListAction.CreateCategory(state.searchQuery))
            }) {
                Icon(Icons.Filled.Add, stringResource(R.string.category_list_create_category))
            }
        }
    ) {
        CategoryListView(modifier = it, state = state) { action ->
            viewModel.onAction(action)
            when (action) {
                is CategoryListAction.SelectCategory -> {
                    onSelectCategory()
                }
                else -> {}
            }
        }

        if (state.isCreateCategorySheetShown) {
            ModalBottomSheet(onDismissRequest = { viewModel.onAction(CategoryListAction.DismissCreateCategorySheet) }) {
                CreateCategoryScreen(
                    viewModel = koinViewModel(),
                    initialName = state.searchQuery
                )
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
                modifier = modifier,
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Text(text = stringResource(R.string.category_list_no_search_results))

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
        contextMenuActions = listOf(
            ContextMenuItem(
                title = stringResource(R.string.category_list_edit_category),
                action = {
                    onAction(CategoryListAction.EditCategory(it))
                }
            ),
            ContextMenuItem(
                title = stringResource(R.string.category_list_delete_category),
                isDestructive = true,
                action = {
                    onAction(CategoryListAction.DeleteCategory(it))
                }
            )
        ),
        onDelete = {
            onAction(CategoryListAction.DeleteCategory(it))
        }
    )
}

@Preview
@Composable
private fun CategoryListPreview() {
    CategoryListView(state = CategoryListState(filteredCategories = listOf(generateMockCategory(), generateMockCategory(), generateMockCategory()))) { }
}