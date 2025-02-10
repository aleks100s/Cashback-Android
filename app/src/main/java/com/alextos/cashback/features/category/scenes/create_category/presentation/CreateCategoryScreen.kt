package com.alextos.cashback.features.category.scenes.create_category.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
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
import com.alextos.cashback.util.views.Screen

@Composable
fun CreateCategoryScreen(
    modifier: Modifier = Modifier,
    viewModel: CreateCategoryViewModel,
    initialName: String
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.onAction(CreateCategoryAction.ChangeCategoryName(initialName))
    }

    Screen(
        modifier = modifier,
        title = stringResource(R.string.create_category_title)
    ) {
        CreateCategoryView(modifier = it, state = state, onAction = viewModel::onAction)
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
        horizontalAlignment = Alignment.Start
    ) {

    }
}

@Preview
@Composable
private fun CreateCategoryPreview() {
    CreateCategoryView(state = CreateCategoryState()) { }
}
