package com.alextos.cashback.features.cards.presentation.add_cashback

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import com.alextos.cashback.core.presentation.views.CustomButton
import com.alextos.cashback.core.presentation.views.CustomWideButton
import com.alextos.cashback.core.presentation.views.SectionView

@Composable
fun AddCashbackScreen(
    viewModel: AddCashbackViewModel,
    selectCategory: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    AddCashbackView(state = state, onAction = viewModel::onAction)
}

@Composable
private fun AddCashbackView(state: AddCashbackState, onAction: (AddCashbackAction) -> Unit) {
    Column(
        modifier = Modifier.padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.add_cashback_title),
            style = MaterialTheme.typography.headlineSmall
        )

        CustomWideButton(state.selectedCategory?.name ?: stringResource(R.string.add_cashback_select_category)) {
            onAction(AddCashbackAction.SelectCategory)
        }

        SectionView(title = stringResource(R.string.add_cashback_percent)) {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                OutlinedTextField(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth(),
                    value = (state.percent * 100).toString(),
                    onValueChange = {
                        onAction(AddCashbackAction.ChangePercent(it.toDouble() / 100))
                    },
                    label = {
                        Text(text = stringResource(R.string.add_cashback_percent))
                    },
                    maxLines = 1,
                    suffix = {
                        Text(text = "%")
                    }
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    state.percentOptions.forEach {
                        CustomButton((it * 100).toString() + "%") {
                            onAction(AddCashbackAction.ChangePercent(it))
                        }
                    }
                }
            }
        }

        Button(
            onClick = {
                onAction(AddCashbackAction.SaveCashback)
            },
            enabled = state.isVaild()
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