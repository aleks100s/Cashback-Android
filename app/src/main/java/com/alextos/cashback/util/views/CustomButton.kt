package com.alextos.cashback.util.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun CustomButton(
    title: String,
    onTap: () -> Unit
) {
    Text(
        modifier = Modifier
            .clickable { onTap() }
            .clip(RoundedCornerShape(8.dp))
            .padding(horizontal = 16.dp, vertical = 12.dp),
        text = title,
        color = MaterialTheme.colorScheme.primary
    )
}