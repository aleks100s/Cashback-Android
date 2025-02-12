package com.alextos.cashback.features.settings.scenes.category_trashbin.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alextos.cashback.R
import com.alextos.cashback.common.UiText
import com.alextos.cashback.common.views.ContextMenuItem
import com.alextos.cashback.common.views.CustomWideButton
import com.alextos.cashback.common.views.RoundedList
import com.alextos.cashback.common.views.Screen
import com.alextos.cashback.core.presentation.views.CategoryItemView

@Composable
fun CategoryTrashbinScreen(
    viewModel: CategoryTrashbinViewModel,
    goBack: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Screen(
        modifier = Modifier,
        title = stringResource(R.string.category_trashbin_title),
        goBack = goBack
    ) {
        CategoryTrashbinView(
            modifier = it,
            state = state,
            onAction = viewModel::onAction
        )
    }
}

@Composable
private fun CategoryTrashbinView(
    modifier: Modifier,
    state: CategoryTrashbinState,
    onAction: (CategoryTrashbinAction) -> Unit
) {
    RoundedList(
        modifier = modifier.padding(horizontal = 16.dp),
        list = state.categories,
        itemView = { modifier, category ->
            CategoryItemView(
                modifier = modifier,
                category = category
            )
        },
        bottomView = {
            CustomWideButton(
                modifier = Modifier.padding(top = 16.dp),
                title = stringResource(R.string.common_restore_all)
            ) {
                onAction(CategoryTrashbinAction.RestoreAll)
            }
        },
        emptyView = {
            Text(text = stringResource(R.string.trashbin_empty))
        },
        onItemClick = {},
        onSwipe = { category ->
            onAction(CategoryTrashbinAction.RestoreCategory(category))
        },
        swipeBackground = Color.Green.copy(alpha = 0.5f),
        swipeText = stringResource(R.string.common_restore),
        contextMenuActions = { category ->
            listOf(
                ContextMenuItem(title = UiText.StringResourceId(R.string.common_restore)) {
                    onAction(CategoryTrashbinAction.RestoreCategory(category))
                }
            )
        }
    )
}