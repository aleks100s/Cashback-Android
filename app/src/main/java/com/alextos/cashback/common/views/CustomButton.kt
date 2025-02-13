package com.alextos.cashback.common.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CustomButton(
    title: String,
    color: Color = MaterialTheme.colorScheme.primary,
    onTap: () -> Unit
) {
    Text(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .clickable { onTap() }
            .padding(horizontal = 16.dp, vertical = 8.dp),
        text = title,
        color = color
    )
}