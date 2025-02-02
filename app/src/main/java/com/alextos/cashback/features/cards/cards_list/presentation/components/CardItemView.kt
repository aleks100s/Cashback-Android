package com.alextos.cashback.features.cards.cards_list.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alextos.cashback.core.domain.Card
import com.alextos.cashback.core.domain.generateMockCard

@Composable
fun CardItemView(
    modifier: Modifier = Modifier,
    card: Card
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.size(16.dp),
                    imageVector = Icons.Default.MailOutline,
                    contentDescription = "Иконка карты",
                    tint = Color(android.graphics.Color.parseColor(card.color ?: "#E7E7E7"))
                )

                Text(text = card.name)
            }

            Text(text = card.currency)
        }

        Surface(
            modifier = Modifier.clip(RoundedCornerShape(8.dp)),
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
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            CategoriesStackView(categories = card.sortedCategories())

                            Icon(
                                modifier = Modifier
                                    .size(24.dp)
                                    .minimumInteractiveComponentSize()
                                    .clickable {
                                        // TODO: Toggle isFavourite
                                    },
                                imageVector = if (card.isFavourite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                contentDescription = "Иконка карты",
                                tint = if (card.isFavourite) Color.Red else Color.Gray
                            )
                        }

                        Text(text = card.toString())
                    }
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