package com.alextos.cashback.common.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CustomDivider() {
    HorizontalDivider(modifier = Modifier
        .background(MaterialTheme.colorScheme.surfaceColorAtElevation(4.dp))
        .padding(start = 64.dp)
    )
}