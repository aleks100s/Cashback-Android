package com.alextos.cashback.core.presentation.views

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alextos.cashback.core.domain.Category
import com.alextos.cashback.core.domain.generateMockCategory

@Composable
fun CategoryIconView(
    modifier: Modifier = Modifier,
    category: Category
) {
    Box(
        modifier = modifier
            .size(44.dp)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color.Red.copy(red = 0.9f, green = 0.4f), Color.Red.copy(red = 0.7f, green = 0.2f))
                ),
                shape = CircleShape
            )
            .border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.background,
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = category.emoji,
            style = MaterialTheme.typography.headlineSmall
        )
    }
}

@Preview
@Composable
private fun CategoryIconPreview() {
    CategoryIconView(category = generateMockCategory())
}