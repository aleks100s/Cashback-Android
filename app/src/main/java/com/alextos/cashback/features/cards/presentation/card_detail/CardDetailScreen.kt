package com.alextos.cashback.features.cards.presentation.card_detail

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alextos.cashback.R
import com.alextos.cashback.core.domain.models.Cashback
import com.alextos.cashback.core.presentation.Screen
import com.alextos.cashback.core.presentation.views.ContextMenu
import com.alextos.cashback.core.presentation.views.ContextMenuItem
import com.alextos.cashback.core.presentation.views.Dialog
import com.alextos.cashback.features.cards.presentation.card_detail.components.CashbackView

@Composable
fun CardDetailScreen(
    modifier: Modifier = Modifier,
    viewModel: CardDetailViewModel,
    onAddCashback: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Screen(
        modifier = modifier,
        title = state.card?.name ?: "",
        floatingActionButton = {
            FloatingActionButton(onClick = {
                onAddCashback()
            }) {
                Icon(Icons.Filled.Add, stringResource(R.string.card_detail_add_cashback))
            }
        }
    ) {
        CardDetailView(
            modifier = it,
            state = state,
            onAction = viewModel::onAction
        )
    }
    state.cashbackToDelete?.let {
        Dialog(
            title = stringResource(R.string.dialog_delete_cashback),
            text = stringResource(R.string.dialog_cannot_undo),
            actionTitle = stringResource(R.string.dialog_delete),
            onConfirm = {
                viewModel.onAction(CardDetailAction.DeleteCashback(it))
            },
            onDismiss = {
                viewModel.onAction(CardDetailAction.DismissDeleteCashbackDialog)
            }
        )
    }
}

@Composable
private fun CardDetailView(
    modifier: Modifier = Modifier,
    state: CardDetailState,
    onAction: (CardDetailAction) -> Unit
) {
    val cashback = state.card?.cashback
    if (!cashback.isNullOrEmpty()) {
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            item {
                Text(
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .padding(vertical = 4.dp),
                    text = stringResource(R.string.card_detail_cashback_list_title)
                )
            }

            cashbackListView(cashback = cashback, onAction = onAction)

            item {
                Text(
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .padding(vertical = 4.dp),
                    text = stringResource(
                        R.string.card_detail_cashback_currency_form,
                        state.card.currency
                    ),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }
    } else {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = stringResource(R.string.card_detail_no_cashback))
        }
    }
}

private fun LazyListScope.cashbackListView(
    cashback: List<Cashback>,
    onAction: (CardDetailAction) -> Unit
) {
    itemsIndexed(cashback) { index, item ->
        val topCornersShape = when (index) {
            0 -> RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
            else -> RectangleShape
        }
        val bottomCornersShape = when (index) {
            cashback.lastIndex -> RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)
            else -> RectangleShape
        }

        ContextMenu(
            element = item,
            content = {
                CashbackView(
                    modifier = Modifier
                        .clip(topCornersShape)
                        .clip(bottomCornersShape)
                        .background(MaterialTheme.colorScheme.surfaceColorAtElevation(4.dp))
                        .padding(vertical = 12.dp, horizontal = 16.dp),
                    cashback = item
                )
            },
            actions = listOf(
                ContextMenuItem(
                    title = stringResource(R.string.card_detail_edit_cashback),
                    action = {
                        onAction(CardDetailAction.EditCashback(item))
                    }
                ),
                ContextMenuItem(
                    title = stringResource(R.string.card_detail_delete_cashback),
                    isDestructive = true,
                    action = {
                        onAction(CardDetailAction.DeleteCashback(item))
                    }
                )
            ),
            onClick = {
                onAction(CardDetailAction.EditCashback(it))
            }
        )

        if (cashback.lastIndex != index) {
            HorizontalDivider()
        }
    }
}

@Preview
@Composable
private fun CardDetailPreview() {
    CardDetailView(state = CardDetailState()) {}
}