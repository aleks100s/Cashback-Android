package com.alextos.cashback.features.cards.scenes.cards_list.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.alextos.cashback.R
import com.alextos.cashback.common.views.CustomButton
import com.alextos.cashback.common.views.SectionView

@Composable
fun CardsSettingsSheet(
    isCompactViewActive: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    onDismiss: () -> Unit
) {
    LazyColumn(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 32.dp),
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        item {
            Text(
                text = stringResource(R.string.cards_settings_title),
                style = MaterialTheme.typography.headlineSmall
            )
        }

        item {
            SectionView(
                title = stringResource(R.string.cards_settings_compact_card_view_title),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(stringResource(R.string.cards_settings_compact_card_view_switch))

                    Switch(
                        checked = isCompactViewActive,
                        onCheckedChange = {
                            onCheckedChange(it)
                        }
                    )
                }
            }
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                CustomButton(title = stringResource(R.string.common_done)) {
                    onDismiss()
                }
            }
        }
    }
}