package com.alextos.cashback.features.places.scenes.place_detail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.alextos.cashback.R
import com.alextos.cashback.common.makeColor

@Composable
fun PlaceCardView(placeCard: PlaceCard) {
    Row(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 12.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.credit_card),
                tint = makeColor(placeCard.card.color),
                contentDescription = placeCard.card.name,
                modifier = Modifier.size(24.dp)
            )

            Text(text = placeCard.card.name)
        }

        Text(text = placeCard.cashback.percentString())
    }
}