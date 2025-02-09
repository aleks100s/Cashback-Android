package com.alextos.cashback.features.category.presentation.category_list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alextos.cashback.R
import com.alextos.cashback.core.presentation.Screen
import com.alextos.cashback.core.presentation.views.RoundedList
import com.alextos.cashback.core.presentation.views.SearchBar
import com.alextos.cashback.features.category.presentation.category_list.components.CategoryItemView

@Composable
fun CategoryListScreen(
    modifier: Modifier = Modifier,
    viewModel: CategoryListViewModel,
    onSelectCategory: () -> Unit,
    onCreateCategory: (String) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Screen(modifier = modifier, title = stringResource(R.string.category_list_title)) {
        CategoryListView(modifier = it, state = state) { action ->
            viewModel.onAction(action)
            when (action) {
                is CategoryListAction.CreateCategory -> {
                    onCreateCategory(action.name)
                }
                is CategoryListAction.SelectCategory -> {
                    onSelectCategory()
                }
                else -> {}
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
        onClick = {
            onAction(CategoryListAction.SelectCategory(it))
        }
    )
}