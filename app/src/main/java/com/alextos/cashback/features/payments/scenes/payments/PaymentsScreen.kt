package com.alextos.cashback.features.payments.scenes.payments

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
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
    Text("Pisya", modifier = modifier)
}