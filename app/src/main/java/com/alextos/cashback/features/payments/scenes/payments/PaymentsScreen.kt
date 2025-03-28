package com.alextos.cashback.features.payments.scenes.payments

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alextos.cashback.R
import com.alextos.cashback.common.views.Screen

@Composable
fun PaymentsScreen(
    viewModel: PaymentsViewModel
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Screen(
        modifier = Modifier,
        title = stringResource(R.string.payments_title)
    ) { modifier ->
        PaymentsView(modifier = modifier, state = state) { action ->
            viewModel.onAction(action)
        }
    }
}

@Composable
private fun PaymentsView(
    modifier: Modifier,
    state: PaymentsState,
    onAction: (PaymentsAction) -> Unit
) {
    LazyColumn(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .fillMaxSize()
    ) {
        items(state.payments, key = { it.id }) { payment ->
            Text(text = "${payment.date.year}-${payment.date.month}-${payment.date.dayOfMonth}")
        }
    }
}