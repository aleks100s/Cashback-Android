package com.alextos.cashback.features.cards.presentation.card_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyScopeMarker
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
        title = state.card?.name ?: ""
    ) {
        CardDetailView(
            modifier = it,
            state = state
        ) { action ->
            when (action) {
                is CardDetailAction.AddCashback -> {
                    onAddCashback()
                }
                else -> viewModel.onAction(action)
            }
        }
    }
}

@Composable
private fun CardDetailView(
    modifier: Modifier = Modifier,
    state: CardDetailState,
    onAction: (CardDetailAction) -> Unit
) {
    if (state.card != null) {
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

            cashbackListView(cashback = state.card.cashback)

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
        Box(modifier = Modifier.fillMaxSize()) {
            Text(text = "Нет сохраненных кэшбэков")
        }
    }
}

private fun LazyListScope.cashbackListView(cashback: List<Cashback>) {
    itemsIndexed(cashback) { index, item ->
        val shape = when (index) {
            0 -> RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp) // Первый элемент
            cashback.lastIndex -> RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp) // Последний элемент
            else -> RectangleShape // Все остальные
        }
        CashbackView(
            modifier = Modifier
                .clickable { }
                .clip(shape)
                .background(MaterialTheme.colorScheme.surfaceColorAtElevation(4.dp))
                .padding(vertical = 12.dp, horizontal = 16.dp),
            cashback = item
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