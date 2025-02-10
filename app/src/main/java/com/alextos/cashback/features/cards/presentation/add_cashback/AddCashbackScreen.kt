package com.alextos.cashback.features.cards.presentation.add_cashback

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alextos.cashback.R
import com.alextos.cashback.util.views.Screen
import com.alextos.cashback.util.views.CustomButton
import com.alextos.cashback.util.views.CustomWideButton
import com.alextos.cashback.util.views.SectionView

@Composable
fun AddCashbackScreen(
    modifier: Modifier = Modifier,
    viewModel: AddCashbackViewModel,
    selectCategory: () -> Unit,
    onSave: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Screen(
        modifier = modifier,
        title = stringResource(R.string.add_cashback_title)
    ) {
        AddCashbackView(modifier = it, state = state, onAction = { action ->
            viewModel.onAction(action)

            when (action) {
                is AddCashbackAction.SaveCashback -> onSave()
                is AddCashbackAction.SelectCategory -> selectCategory()
                else -> {}
            }
        })
    }
}

@Composable
private fun AddCashbackView(
    modifier: Modifier = Modifier,
    state: AddCashbackState,
    onAction: (AddCashbackAction) -> Unit
) {
    Column(
        modifier = modifier.padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CustomWideButton(state.selectedCategory?.name ?: stringResource(R.string.add_cashback_select_category)) {
            onAction(AddCashbackAction.SelectCategory)
        }

        SectionView(title = stringResource(R.string.add_cashback_percent)) {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                OutlinedTextField(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth(),
                    value = state.percent,
                    onValueChange = {
                        onAction(AddCashbackAction.ChangePercent(it))
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
                        CustomButton("$it%") {
                            onAction(AddCashbackAction.ChangePercent(it.toString()))
                        }
                    }
                }
            }
        }

        Button(
            onClick = {
                onAction(AddCashbackAction.SaveCashback)
            },
            enabled = state.isValid
        ) {
            Text(text = stringResource(R.string.common_save))
        }
    }
}

@Preview
@Composable
fun AddCashbackPreview() {
    AddCashbackView(state = AddCashbackState()) {}
}