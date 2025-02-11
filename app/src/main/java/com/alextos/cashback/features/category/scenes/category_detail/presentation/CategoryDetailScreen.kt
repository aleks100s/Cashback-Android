package com.alextos.cashback.features.category.scenes.category_detail.presentation

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.alextos.cashback.R
import com.alextos.cashback.common.views.CustomTextField
import com.alextos.cashback.common.views.Screen

@Composable
fun CategoryDetailScreen(
    modifier: Modifier = Modifier,
    viewModel: CategoryDetailViewModel,
    navController: NavController,
    onSaved: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Screen(
        modifier = modifier,
        title = state.title.asString(),
        navController = navController
    ) {
        CategoryDetailView(modifier = it, state = state) { action ->
            viewModel.onAction(action)
            when (action) {
                is CategoryDetailAction.SaveButtonTapped -> {
                    onSaved()
                }
                else -> {}
            }
        }
    }
}

@Composable
private fun CategoryDetailView(
    modifier: Modifier = Modifier,
    state: CategoryDetailState,
    onAction: (CategoryDetailAction) -> Unit
) {
    Column(
        modifier = modifier
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CustomTextField(
            value = state.categoryName,
            onValueChange = {
                onAction(CategoryDetailAction.ChangeCategoryDetailName(it))
            },
            label = stringResource(R.string.category_detail_name)
        )

        CustomTextField(
            value = state.emoji,
            onValueChange = {
                onAction(CategoryDetailAction.ChangeCategoryDetailEmoji(it))
            },
            label = stringResource(R.string.category_detail_emoji),
            footer = stringResource(R.string.category_detail_optional)
        )

        CustomTextField(
            value = state.description,
            onValueChange = {
                onAction(CategoryDetailAction.ChangeCategoryDetailDescription(it))
            },
            label = stringResource(R.string.category_detail_description),
            footer = stringResource(R.string.category_detail_optional)
        )

        Button(
            onClick = {
                onAction(CategoryDetailAction.SaveButtonTapped)
            },
            enabled = state.isValid
        ) {
            Text(stringResource(R.string.common_save))
        }
    }
}

@Preview
@Composable
private fun CategoryDetailPreview() {
    CategoryDetailView(state = CategoryDetailState()) { }
}
