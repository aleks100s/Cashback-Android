package com.alextos.cashback.features.category.presentation.category_list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alextos.cashback.R
import com.alextos.cashback.core.presentation.Screen

@Composable
fun CategoryListScreen(
    modifier: Modifier = Modifier,
    viewModel: CategoryListViewModel,
    onCreateCategory: (String) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Screen(modifier = modifier, title = stringResource(R.string.category_list_title)) {
        CategoryListView(modifier = it, state = state) { action ->
            when (action) {
                is CategoryListAction.CreateCategory -> {
                    onCreateCategory(action.name)
                }
                else -> {}
            }
            viewModel.onAction(action)
        }
    }
}

@Composable
private fun CategoryListView(
    modifier: Modifier = Modifier,
    state: CategoryListState,
    onAction: (CategoryListAction) -> Unit
) {
    if (state.filteredCategories.isEmpty()) {
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
    } else {
        LazyColumn(
            modifier = modifier.fillMaxSize()
        ) {
            items(state.filteredCategories) { category ->
                Text(category.name)
            }
        }
    }
}