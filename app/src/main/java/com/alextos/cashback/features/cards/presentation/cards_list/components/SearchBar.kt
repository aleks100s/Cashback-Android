package com.alextos.cashback.features.cards.presentation.cards_list.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.alextos.cashback.R

@Composable
fun SearchBar(
    value: String,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = value,
        onValueChange = onValueChange,
        label = {
            Text(text = stringResource(R.string.cards_list_search_placeholder))
        },
        singleLine = true,
        trailingIcon = {
            AnimatedVisibility(
                visible = value.isNotEmpty(),
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Icon(
                    modifier = Modifier
                        .size(24.dp)
                        .minimumInteractiveComponentSize()
                        .clickable {
                            onValueChange("")
                        },
                    imageVector = Icons.Default.Clear,
                    contentDescription = stringResource(R.string.cards_list_search_clear),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    )
}