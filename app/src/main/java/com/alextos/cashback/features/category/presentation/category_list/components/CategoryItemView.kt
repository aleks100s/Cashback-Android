package com.alextos.cashback.features.category.presentation.category_list.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.alextos.cashback.R
import com.alextos.cashback.core.domain.models.Category
import com.alextos.cashback.core.presentation.views.CategoryIconSize
import com.alextos.cashback.core.presentation.views.CategoryIconView
import com.alextos.cashback.core.presentation.views.Dialog

@Composable
fun CategoryItemView(
    modifier: Modifier = Modifier,
    category: Category
) {
    var isDialogShown by remember { mutableStateOf(false) }

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        CategoryIconView(category = category, size = CategoryIconSize.Medium)

        Text(text = category.name)

        if (!category.info.isNullOrEmpty()) {
            Icon(
                modifier = Modifier
                    .minimumInteractiveComponentSize()
                    .clickable {
                        isDialogShown = true
                    },
                imageVector = Icons.Filled.Info,
                contentDescription = stringResource(R.string.category_list_item_info)
            )
        }

        if (isDialogShown) {
            Dialog(
                title = stringResource(R.string.category_list_item_description, category.name),
                text = category.info ?: "",
                actionTitle = stringResource(R.string.common_ok),
                onConfirm = {
                    isDialogShown = false
                },
                onDismiss = {
                    isDialogShown = false
                }
            )
        }
    }
}