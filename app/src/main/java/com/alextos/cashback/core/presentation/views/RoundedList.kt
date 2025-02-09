package com.alextos.cashback.core.presentation.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp

interface ListElement {
    val id: String
}

@Composable
fun <Element: ListElement> RoundedList(
    modifier: Modifier = Modifier,
    list: List<Element>,
    itemView: @Composable (Modifier, Element) -> Unit,
    onClick: (Element) -> Unit
) {
    LazyColumn(modifier = modifier) {
        items(items = list, key = { it.id }) { item ->
            val topCornersShape = if (item == list.firstOrNull()) {
                RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
            } else RectangleShape

            val bottomCornersShape = if (item == list.lastOrNull()) {
                RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)
            } else RectangleShape

            itemView(
                Modifier
                    .animateItem()
                    .clickable {
                        onClick(item)
                    }
                    .clip(topCornersShape)
                    .clip(bottomCornersShape)
                    .background(MaterialTheme.colorScheme.surfaceColorAtElevation(4.dp))
                    .padding(vertical = 12.dp, horizontal = 16.dp),
                item
            )

            if (list.lastOrNull() != item) {
                HorizontalDivider()
            } else {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}
