package com.alextos.cashback.features.cards.scenes.cashback_detail.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alextos.cashback.R
import com.alextos.cashback.common.views.Screen
import com.alextos.cashback.common.views.CustomButton
import com.alextos.cashback.common.views.CustomLabel
import com.alextos.cashback.common.views.CustomWideButton
import com.alextos.cashback.common.views.SectionView
import com.alextos.cashback.core.presentation.views.CategoryItemView

@Composable
fun CashbackDetailScreen(
    modifier: Modifier = Modifier,
    viewModel: CashbackDetailViewModel,
    goBack: () -> Unit,
    selectCategory: () -> Unit,
    onSave: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val haptic = LocalHapticFeedback.current

    Screen(
        modifier = modifier,
        title = state.title.asString(),
        goBack = goBack,
        floatingActionButton = {
            Button(
                onClick = {
                    viewModel.onAction(CashbackDetailAction.SaveCashbackDetail)
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                    onSave()
                },
                enabled = state.isValid
            ) {
                CustomLabel(
                    title = stringResource(R.string.common_save),
                    imageVector = Icons.Default.Done
                )
            }
        }
    ) {
        AddCashbackView(modifier = it, state = state, onAction = { action ->
            viewModel.onAction(action)

            when (action) {
                is CashbackDetailAction.SelectCategory -> selectCategory()
                else -> {}
            }
        })
    }
}

@Composable
private fun AddCashbackView(
    modifier: Modifier = Modifier,
    state: CashbackDetailState,
    onAction: (CashbackDetailAction) -> Unit
) {
    val haptic = LocalHapticFeedback.current

    Column(
        modifier = modifier.padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (state.selectedCategory != null) {
            CategoryItemView(
                modifier = Modifier
                    .clickable {
                        onAction(CashbackDetailAction.SelectCategory)
                    }
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.surfaceColorAtElevation(4.dp))
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                category = state.selectedCategory
            )
        } else {
            CustomWideButton(title = stringResource(R.string.add_cashback_select_category)) {
                onAction(CashbackDetailAction.SelectCategory)
            }
        }

        SectionView(title = stringResource(R.string.add_cashback_percent)) {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                OutlinedTextField(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth(),
                    value = state.percent,
                    onValueChange = {
                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                        onAction(CashbackDetailAction.ChangePercent(it))
                    },
                    label = {
                        Text(text = stringResource(R.string.add_cashback_percent))
                    },
                    maxLines = 1,
                    suffix = {
                        Text(text = "%")
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    state.percentOptions.forEach {
                        CustomButton(title = "$it%") {
                            onAction(CashbackDetailAction.ChangePercent(it.toString()))
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun AddCashbackPreview() {
    AddCashbackView(state = CashbackDetailState()) {}
}