package com.alextos.cashback.features.cards.scenes.cards_list.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alextos.cashback.R
import com.alextos.cashback.common.views.ColorPicker

@Composable
fun AddCardSheet(
    cardName: String,
    onValueChange: (String) -> Unit,
    color: String,
    onColorChange: (String) -> Unit,
    onSaveTapped: () -> Unit
) {
    Column(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        Text(
            text = stringResource(R.string.add_card_sheet_title),
            style = MaterialTheme.typography.headlineSmall
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = cardName,
            onValueChange = onValueChange,
            singleLine = true,
            label = {
                Text(stringResource(R.string.add_card_sheet_placeholder))
            }
        )

        ColorPicker(
            title = stringResource(R.string.add_card_sheet_pick_color),
            color = color,
            onColorChange = onColorChange
        )

        Button(onClick = onSaveTapped, enabled = cardName.isNotEmpty()) {
            Text(stringResource(R.string.common_save))
        }
    }
}
