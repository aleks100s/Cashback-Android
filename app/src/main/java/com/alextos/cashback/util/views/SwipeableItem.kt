package com.alextos.cashback.util.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.alextos.cashback.R

@Composable
fun SwipeableItem(
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit,
    onDelete: () -> Boolean
) {
    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = {
            if (it == SwipeToDismissBoxValue.EndToStart) {
                return@rememberSwipeToDismissBoxState onDelete()
            }
            return@rememberSwipeToDismissBoxState false
        },
        positionalThreshold = {
            it * 0.5f
        }
    )

    SwipeToDismissBox(
        state = dismissState,
        backgroundContent = {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .background(Color.Red)
                    .padding(16.dp),
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = stringResource(R.string.common_remove))
            }
        },
        enableDismissFromStartToEnd = false,
        content = content
    )
}
