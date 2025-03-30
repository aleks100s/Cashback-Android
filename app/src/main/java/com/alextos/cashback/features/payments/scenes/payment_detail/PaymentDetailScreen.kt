package com.alextos.cashback.features.payments.scenes.payment_detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alextos.cashback.R
import com.alextos.cashback.common.views.CustomDatePicker
import com.alextos.cashback.common.views.CustomLabel
import com.alextos.cashback.common.views.PickerDropdown
import com.alextos.cashback.common.views.Screen
import com.alextos.cashback.common.views.SectionView
import com.alextos.cashback.core.domain.models.currency.symbol

@Composable
fun PaymentDetailScreen(
    viewModel: PaymentDetailViewModel,
    goBack: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Screen(
        modifier = Modifier,
        title = stringResource(R.string.add_payment_title),
        goBack = goBack,
        floatingActionButton = {
            Button(onClick = {
                viewModel.onAction(PaymentDetailAction.SaveButtonTapped)
                goBack()
            }, enabled = state.isEnabled()) {
                CustomLabel(title = stringResource(R.string.common_save), imageVector = Icons.Default.Check)
            }
        }
    ) { modifier ->
        PaymentDetailView(modifier, state) { action ->
            viewModel.onAction(action)
        }
    }
}

@Composable
private fun PaymentDetailView(
    modifier: Modifier,
    state: PaymentDetailState,
    onAction: (PaymentDetailAction) -> Unit
) {
    Column(
        modifier = modifier.padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        SectionView(title = stringResource(R.string.payment_detail_source_section_title)) {
            PickerDropdown(
                modifier = Modifier.fillMaxWidth(),
                selected = state.selectedCard, options = state.availableCards
            ) {
                onAction(PaymentDetailAction.CardSelected(it))
            }
        }

        SectionView(stringResource(R.string.payment_detail_date_section_title)) {
            CustomDatePicker(state.date) {
                onAction(PaymentDetailAction.DateChanged(it))
            }
        }

        SectionView(stringResource(R.string.payment_detail_amount_section_title)) {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = state.amount,
                onValueChange = {
                    onAction(PaymentDetailAction.AmountChanged(it))
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                suffix = {
                    Text(text = state.selectedCard?.currency?.symbol ?: "")
                },
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Color.Transparent, unfocusedBorderColor = Color.Transparent)
            )
        }
    }
}