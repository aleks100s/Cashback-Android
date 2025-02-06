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
import com.alextos.cashback.core.domain.models.Category
import com.alextos.cashback.core.domain.models.generateMockCategory

sealed interface CategoryIconSize {
    data object Small: CategoryIconSize
    data object Medium: CategoryIconSize
}

@Composable
fun CategoryIconView(
    modifier: Modifier = Modifier,
    category: Category,
    size: CategoryIconSize
) {
    val circleSize = when (size) {
        is CategoryIconSize.Small -> {
            44.dp
        }
        is CategoryIconSize.Medium -> {
            56.dp
        }
    }

    val borderWidth = when (size) {
        is CategoryIconSize.Small -> {
            2.dp
        }
        is CategoryIconSize.Medium -> {
            0.dp
        }
    }

    val textStyle = when (size) {
        is CategoryIconSize.Small -> {
            MaterialTheme.typography.headlineSmall
        }
        is CategoryIconSize.Medium -> {
            MaterialTheme.typography.headlineMedium
        }
    }

    Box(
        modifier = modifier
            .size(circleSize)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color.Red.copy(red = 0.9f, green = 0.4f), Color.Red.copy(red = 0.7f, green = 0.2f))
                ),
                shape = CircleShape
            )
            .border(
                width = borderWidth,
                color = MaterialTheme.colorScheme.background,
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = category.emoji,
            style = textStyle
        )
    }
}

@Preview
@Composable
private fun CategoryIconPreview() {
    CategoryIconView(category = generateMockCategory(), size = CategoryIconSize.Small)
}