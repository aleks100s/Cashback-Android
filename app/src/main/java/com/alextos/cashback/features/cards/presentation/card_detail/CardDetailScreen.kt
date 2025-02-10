package com.alextos.cashback.features.cards.presentation.card_detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import com.alextos.cashback.core.domain.models.Cashback
import com.alextos.cashback.util.views.Screen
import com.alextos.cashback.util.views.ContextMenuItem
import com.alextos.cashback.util.views.Dialog
import com.alextos.cashback.features.cards.presentation.card_detail.components.CashbackView
import com.alextos.cashback.util.UiText
import com.alextos.cashback.util.views.RoundedList

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
    val list = state.card?.cashback ?: emptyList()

    RoundedList(
        modifier = modifier.padding(horizontal = 16.dp),
        list = list,
        itemView = { modifier, cashback ->
            CashbackView(modifier = modifier, cashback = cashback)
        },
        emptyView = {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = stringResource(R.string.card_detail_no_cashback))
            }
        },
        topView = {
            Text(
                modifier = Modifier.padding(start = 8.dp).padding(vertical = 4.dp),
                text = stringResource(R.string.card_detail_cashback_list_title)
            )
        },
        bottomView = {
            Text(
                modifier = Modifier.padding(start = 8.dp).padding(vertical = 4.dp),
                text = stringResource(R.string.card_detail_cashback_currency_form, state.card?.currency ?: ""),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.secondary
            )
        },
        onItemClick = {
            onAction(CardDetailAction.EditCashback(it))
        },
        onDelete = {
            onAction(CardDetailAction.DeleteCashback(it))
        },
        contextMenuActions = {
            listOf(
                ContextMenuItem(
                    title = UiText.StringResourceId(R.string.card_detail_edit_cashback),
                    action = {
                        onAction(CardDetailAction.EditCashback(it))
                    }
                ),
                ContextMenuItem(
                    title = UiText.StringResourceId(R.string.card_detail_delete_cashback),
                    isDestructive = true,
                    action = {
                        onAction(CardDetailAction.DeleteCashback(it))
                    }
                )
            )
        }
    )
}

@Preview
@Composable
private fun CardDetailPreview() {
    CardDetailView(state = CardDetailState()) {}
}