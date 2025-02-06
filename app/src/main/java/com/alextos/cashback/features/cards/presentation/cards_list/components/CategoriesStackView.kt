package com.alextos.cashback.features.cards.presentation.cards_list.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.alextos.cashback.core.domain.models.Category
import com.alextos.cashback.core.domain.models.generateMockCategory
import com.alextos.cashback.core.presentation.views.CategoryIconSize
import com.alextos.cashback.core.presentation.views.CategoryIconView

@Composable
fun CategoriesStackView(
    modifier: Modifier = Modifier,
    categories: List<Category>
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        categories.forEachIndexed { index, category ->
            CategoryIconView(
                modifier = modifier.offset(x = (-12).dp * index).zIndex(index.toFloat()),
                category = category,
                size = CategoryIconSize.Small
            )
        }
    }
}

@Preview
@Composable
private fun CategoriesStackPreview() {
    CategoriesStackView(categories = listOf(
        generateMockCategory(),
        generateMockCategory(),
        generateMockCategory(),
        generateMockCategory()
    ))
}