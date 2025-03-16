package com.alextos.cashback.features.cards.scenes.cards_list.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.alextos.cashback.R
import com.alextos.cashback.common.toHex
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
            SectionView(
                title = stringResource(R.string.add_card_sheet_pick_color),
                footer = stringResource(R.string.add_card_sheet_color_hint)
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    ColorPicker(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                        title = stringResource(R.string.add_card_pick_color_from_palette),
                        color = color,
                        onColorChange = onColorChange
                    )

                    Row(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(stringResource(R.string.add_card_quick_color_picker))

                        listOf(Color.Red, Color.Green, Color.Blue, Color.Yellow).forEach { preset ->
                            Box(
                                modifier = Modifier
                                    .minimumInteractiveComponentSize()
                                    .clickable {
                                        onColorChange(preset.toHex())
                                    }
                                    .clip(RoundedCornerShape(50))
                                    .background(preset)
                                    .weight(1f)
                                    .height(24.dp)
                            ) {}
                        }
                    }
                }
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
