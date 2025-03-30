package com.alextos.cashback.features.payments.scenes.payments

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alextos.cashback.R
import com.alextos.cashback.common.UiText
import com.alextos.cashback.common.makeColor
import com.alextos.cashback.common.views.ContextMenuItem
import com.alextos.cashback.common.views.CustomButton
import com.alextos.cashback.common.views.CustomDivider
import com.alextos.cashback.common.views.CustomLabel
import com.alextos.cashback.common.views.EmptyView
import com.alextos.cashback.common.views.RoundedList
import com.alextos.cashback.common.views.Screen
import com.alextos.cashback.common.views.SectionView
import com.alextos.cashback.core.domain.models.Payment
import com.alextos.cashback.core.domain.models.currency.Currency
import com.alextos.cashback.features.payments.scenes.payments.components.PaymentItemView
import io.github.dautovicharis.charts.PieChart
import io.github.dautovicharis.charts.model.toChartDataSet
import io.github.dautovicharis.charts.style.ChartViewDefaults
import io.github.dautovicharis.charts.style.PieChartDefaults
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun PaymentsScreen(
    viewModel: PaymentsViewModel,
    onCreatePayment: () -> Unit,
    onEditPayment: (Payment) -> Unit
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
        },
        floatingActionButton = {
            Button(onClick = {
                onCreatePayment()
            }) {
                CustomLabel(
                    title = stringResource(R.string.payments_add_button_title),
                    imageVector = Icons.Filled.Add
                )
            }
        }
    ) { modifier ->
        PaymentsView(modifier = modifier, state = state) { action ->
            viewModel.onAction(action)
            when (action) {
                is PaymentsAction.PaymentSelected -> onEditPayment(action.payment)
                else -> {}
            }
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
        list = if (state.isAllTimePeriod) state.allPayments else  state.periodPayments,
        topView = {
            val formatter = remember { DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale("ru_RU")) }
            val title = stringResource(
                R.string.paymnets_period_dates,
                formatter.format(state.startPeriod),
                formatter.format(state.endPeriod)
            )

            Column(
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                if (state.isChartVisible()) {
                    ChartView(state, title)
                }

                CurrencyDataView(state)

                if (!state.isAllTimePeriod) {
                    PeriodView(state, title, onAction)
                }
            }
        },
        itemView = { modifier, payment ->
            PaymentItemView(modifier, payment)
        },
        onItemClick = {
            onAction(PaymentsAction.PaymentSelected(it))
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

@Composable
private fun CurrencyDataView(state: PaymentsState) {
    SectionView {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = stringResource(R.string.rubles, state.currencyData.rubles))

            Text(text = stringResource(R.string.common_rubles), color = MaterialTheme.colorScheme.secondary)
        }

        CustomDivider()

        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = stringResource(R.string.miles, state.currencyData.miles))

            Text(text = stringResource(R.string.common_miles), color = MaterialTheme.colorScheme.secondary)
        }

        CustomDivider()

        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = stringResource(R.string.points, state.currencyData.points))

            Text(text = stringResource(R.string.common_points), color = MaterialTheme.colorScheme.secondary)
        }
    }
}

@Composable
private fun ChartView(state: PaymentsState, title: String) {
    val payments = state.chartData
    val pieColors = payments.map { makeColor(it.card.color) }
    val style = PieChartDefaults.style(
        pieColor = Color.Transparent,
        borderColor = Color.Transparent,
        donutPercentage = 60f,
        borderWidth = 6f,
        pieColors = pieColors,
        chartViewStyle = ChartViewDefaults.style(
            shadow = 0.dp,
            outerPadding = 0.dp,
            innerPadding = 0.dp
        )
    )

    val dataSet = payments.map { it.totalAmount }.toChartDataSet(title = if (state.isAllTimePeriod)  stringResource(R.string.chart_all_time) else title)

    Box {
        PieChart(dataSet = dataSet, style = style)
    }
}

@Composable
private fun PeriodView(
    state: PaymentsState,
    title: String,
    onAction: (PaymentsAction) -> Unit
) {
    SectionView {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.KeyboardArrowLeft,
                    tint = if (state.isPreviousButtonEnabled) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
                    contentDescription = stringResource(R.string.payments_preiod_previous),
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .size(24.dp)
                )

                CustomButton(
                    title = stringResource(R.string.payments_preiod_previous),
                    enabled = state.isPreviousButtonEnabled
                ) {
                    onAction(PaymentsAction.PreviousMonth)
                }
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                CustomButton(
                    title = stringResource(R.string.payments_period_next),
                    enabled = state.isNextButtonEnabled
                ) {
                    onAction(PaymentsAction.NextMonth)
                }

                Icon(
                    imageVector = Icons.AutoMirrored.Default.KeyboardArrowRight,
                    tint = if (state.isNextButtonEnabled) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
                    contentDescription = stringResource(R.string.payments_period_next),
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .size(24.dp)
                )
            }
        }
    }

    Text(
        text = title,
        color = MaterialTheme.colorScheme.secondary
    )
}