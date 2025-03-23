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
    size: CategoryIconSize,
    color: Color = MaterialTheme.colorScheme.primary
) {
    val circleSize = when (size) {
        is CategoryIconSize.Small -> {
            44.dp
        }
        is CategoryIconSize.Medium -> {
            48.dp
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
            MaterialTheme.typography.headlineSmall
        }
    }

    Box(
        modifier = modifier
            .size(circleSize)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        color.copy(red = color.red * 0.75f, green = color.green * 0.75f, blue = color.blue * 0.75f),
                        color.copy(red = color.red * 0.5f, green = color.green * 0.5f, blue = color.blue * 0.5f)
                    )
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