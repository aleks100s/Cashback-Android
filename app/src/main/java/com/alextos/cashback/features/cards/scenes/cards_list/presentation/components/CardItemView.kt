package com.alextos.cashback.features.cards.scenes.cards_list.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alextos.cashback.R
import com.alextos.cashback.common.makeColor
import com.alextos.cashback.core.domain.models.Card
import com.alextos.cashback.core.domain.models.generateMockCard

@Composable
fun CardItemView(
    modifier: Modifier = Modifier,
    card: Card,
    query: String,
    onClick: () -> Unit = {},
    onFavouriteTap: () -> Unit = {}
) {
    val haptic = LocalHapticFeedback.current
    val color = makeColor(card.color)

    Box(
        modifier = modifier
            .border(
                width = 2.dp,
                brush = Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.secondary.copy(0.4f),
                        Color.White.copy(0f),
                        color.copy(alpha = 0.4f)
                    ),
                ),
                shape = RoundedCornerShape(20.dp)
            )
            .clip(RoundedCornerShape(20.dp))
            .clickable(onClick = onClick)
            .background(Brush.linearGradient(
                colors = listOf(
                    Color.White.copy(alpha = 0.3f),
                    Color.White.copy(alpha = 0.1f)
                ),
                start = Offset.Zero,
                end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
            ))
            .background(color = color.copy(0.4f))
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.Start
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = card.name,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Black,
                    color = MaterialTheme.colorScheme.onSurface.copy(0.65f)
                )

                Icon(
                    modifier = Modifier
                        .minimumInteractiveComponentSize()
                        .clip(CircleShape)
                        .clickable {
                            onFavouriteTap()
                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                        }
                        .size(32.dp),
                    imageVector = if (card.isFavourite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = stringResource(R.string.cards_list_item_favourite),
                    tint = if (card.isFavourite) Color.Red else Color.Gray
                )
            }

            if (card.isEmpty()) {
                Text(
                    text = card.toString(),
                )
            } else {
                Column {
                    CategoriesStackView(
                        categories = card.sortedCategories(),
                        color = color
                    )

                    Text(
                        text = card.toStringWith(query),
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = card.currencySymbol,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Black,
                    color = MaterialTheme.colorScheme.onSurface.copy(0.65f)
                )
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0x000000)
@Composable
private fun CardItemPreview() {
    Surface(modifier = Modifier.padding(8.dp)) {
        CardItemView(card = generateMockCard(), query = "") {}
    }
}