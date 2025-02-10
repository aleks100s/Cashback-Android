package com.alextos.cashback.features.category.scenes.create_category.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alextos.cashback.R
import com.alextos.cashback.util.views.CustomTextField
import com.alextos.cashback.util.views.CustomWideButton
import com.alextos.cashback.util.views.Screen

@Composable
fun CreateCategoryScreen(
    modifier: Modifier = Modifier,
    viewModel: CreateCategoryViewModel,
    onSaved: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Screen(
        modifier = modifier,
        title = stringResource(R.string.create_category_title)
    ) {
        CreateCategoryView(modifier = it, state = state) { action ->
            viewModel.onAction(action)
            when (action) {
                is CreateCategoryAction.SaveButtonTapped -> {
                    onSaved()
                }
                else -> {}
            }
        }
    }
}

@Composable
private fun CreateCategoryView(
    modifier: Modifier = Modifier,
    state: CreateCategoryState,
    onAction: (CreateCategoryAction) -> Unit
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
                onAction(CreateCategoryAction.ChangeCategoryName(it))
            },
            label = stringResource(R.string.create_category_name)
        )

        CustomTextField(
            value = state.emoji,
            onValueChange = {
                onAction(CreateCategoryAction.ChangeCategoryEmoji(it))
            },
            label = stringResource(R.string.create_category_emoji),
            footer = stringResource(R.string.create_category_optional)
        )

        CustomTextField(
            value = state.description,
            onValueChange = {
                onAction(CreateCategoryAction.ChangeCategoryDescription(it))
            },
            label = stringResource(R.string.create_category_description),
            footer = stringResource(R.string.create_category_optional)
        )

        Button(
            onClick = {
                onAction(CreateCategoryAction.SaveButtonTapped)
            },
            enabled = state.isValid
        ) {
            Text(stringResource(R.string.common_save))
        }
    }
}

@Preview
@Composable
private fun CreateCategoryPreview() {
    CreateCategoryView(state = CreateCategoryState()) { }
}
