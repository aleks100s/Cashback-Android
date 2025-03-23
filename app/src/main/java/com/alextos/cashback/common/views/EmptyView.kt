package com.alextos.cashback.common.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun EmptyView(
    title: String,
    icon: ImageVector
) {
    Box(
        modifier = Modifier
            .padding(horizontal = 32.dp)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
                modifier = Modifier.size(44.dp)
            )

            Text(
                text = title,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun EmptyView(
    title: String,
    painter: Painter
) {
    Box(
        modifier = Modifier
            .padding(horizontal = 32.dp)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                painter = painter,
                tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
                contentDescription = title
            )

            Text(
                text = title,
                textAlign = TextAlign.Center
            )
        }
    }
}