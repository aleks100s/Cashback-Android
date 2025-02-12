package com.alextos.cashback.core.presentation.views

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
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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
    onFavouriteTap: () -> Unit
) {
    val haptic = LocalHapticFeedback.current

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.size(20.dp),
                    painter = painterResource(R.drawable.credit_card),
                    contentDescription = stringResource(R.string.cards_list_card_icon),
                    tint = makeColor(card.color)
                )

                Text(text = card.name)
            }

            Text(text = card.currency)
        }

        Surface(
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .fillMaxWidth(),
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
                                        onFavouriteTap()
                                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                    },
                                imageVector = if (card.isFavourite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                contentDescription = stringResource(R.string.cards_list_item_favourite),
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
        CardItemView(card = generateMockCard()) {}
    }
}