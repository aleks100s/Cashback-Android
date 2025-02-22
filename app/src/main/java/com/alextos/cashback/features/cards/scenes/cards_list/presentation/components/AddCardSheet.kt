package com.alextos.cashback.features.cards.scenes.cards_list.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.alextos.cashback.R
import com.alextos.cashback.common.views.ColorPicker
import com.alextos.cashback.common.views.PickerDropdown
import com.alextos.cashback.common.views.SectionView
import com.alextos.cashback.core.domain.models.currency.Currency

@Composable
fun AddCardSheet(
    cardName: String,
    onValueChange: (String) -> Unit,
    color: String,
    onColorChange: (String) -> Unit,
    currency: Currency,
    onCurrencyChange: (Currency) -> Unit,
    onSaveTapped: () -> Unit
) {
    LazyColumn(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        item {
            Text(
                text = stringResource(R.string.add_card_sheet_title),
                style = MaterialTheme.typography.headlineSmall
            )
        }

        item {
            SectionView(footer = stringResource(R.string.add_card_sheet_name_hint)) {
                OutlinedTextField(
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                        .fillMaxWidth(),
                    value = cardName,
                    onValueChange = onValueChange,
                    singleLine = true,
                    label = {
                        Text(stringResource(R.string.add_card_sheet_placeholder))
                    }
                )
            }
        }

        item {
            SectionView(footer = stringResource(R.string.add_card_sheet_color_hint)) {
                ColorPicker(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                    title = stringResource(R.string.add_card_sheet_pick_color),
                    color = color,
                    onColorChange = onColorChange
                )
            }
        }

        item {
            SectionView(footer = stringResource(R.string.add_card_sheet_select_currency_footer)) {
                Row(
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = stringResource(R.string.add_card_sheet_select_currency))

                    PickerDropdown(
                        modifier = Modifier.weight(1f),
                        selected = currency,
                        options = Currency.entries
                    ) {
                        onCurrencyChange(it)
                    }
                }
            }
        }

        item {
            Button(onClick = onSaveTapped, enabled = cardName.isNotEmpty()) {
                Text(stringResource(R.string.common_save))
            }
        }
    }
}
