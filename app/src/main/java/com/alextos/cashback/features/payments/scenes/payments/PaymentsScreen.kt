package com.alextos.cashback.features.payments.scenes.payments

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alextos.cashback.R
import com.alextos.cashback.common.UiText
import com.alextos.cashback.common.views.ContextMenuItem
import com.alextos.cashback.common.views.CustomButton
import com.alextos.cashback.common.views.EmptyView
import com.alextos.cashback.common.views.RoundedList
import com.alextos.cashback.common.views.Screen
import com.alextos.cashback.features.payments.scenes.payments.components.PaymentItemView

@Composable
fun PaymentsScreen(
    viewModel: PaymentsViewModel
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val haptic = LocalHapticFeedback.current

    Screen(
        modifier = Modifier,
        title = stringResource(R.string.payments_title),
        actions = {
            CustomButton(
                title = if (state.isAllTimePeriod) {
                    stringResource(R.string.payments_month_mode)
                } else {
                    stringResource(R.string.payments_all_time_mode)
                }
            ) {
                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                viewModel.onAction(PaymentsAction.TogglePeriodMode)
            }
        }
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
    RoundedList(
        modifier = modifier.padding(horizontal = 16.dp),
        list = state.payments,
        topView = {

        },
        itemView = { modifier, payment ->
            PaymentItemView(modifier, payment)
        },
        emptyView = {
            EmptyView(
                title = stringResource(R.string.payments_empty_period),
                imageVector = Icons.Default.ShoppingCart
            )
        },
        contextMenuActions = { element ->
            listOf(
                ContextMenuItem(
                    title = UiText.StringResourceId(R.string.common_remove),
                    isDestructive = true,
                    action = {
                        onAction(PaymentsAction.DeletePayment(element))
                    }
                )
            )
        },
        allowSwipe = false
    )
}