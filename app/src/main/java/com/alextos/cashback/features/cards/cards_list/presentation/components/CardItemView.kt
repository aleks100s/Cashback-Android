package com.alextos.cashback.features.cards.cards_list.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alextos.cashback.core.domain.Card
import com.alextos.cashback.core.domain.generateMockCard

@Composable
fun CardItemView(
    modifier: Modifier = Modifier,
    card: Card
) {
    Surface(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp)),
        tonalElevation = 8.dp,
        shadowElevation = 8.dp
    ) {
        Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
            if (card.isEmpty()) {
                Text(text = card.toString())
            } else {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.Start
                ) {
                    CategoriesStackView(categories = card.sortedCategories())

                    Text(text = card.toString())
                }
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0x000000)
@Composable
private fun CardItemPreview() {
    Surface(modifier = Modifier.padding(8.dp)) {
        CardItemView(card = generateMockCard())
    }
}