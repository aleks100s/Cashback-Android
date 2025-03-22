package com.alextos.cashback.common.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.alextos.cashback.R

@Composable
fun FavouriteButton(
    isFavourite: Boolean,
    onFavouriteToggle: () -> Unit
) {
    val haptic = LocalHapticFeedback.current

    Icon(
        modifier = Modifier
            .minimumInteractiveComponentSize()
            .clip(CircleShape)
            .clickable {
                onFavouriteToggle()
                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
            }
            .size(32.dp),
        imageVector = if (isFavourite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
        contentDescription = stringResource(R.string.cards_list_item_favourite),
        tint = if (isFavourite) Color.Red else Color.Gray
    )
}