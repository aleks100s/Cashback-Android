package com.alextos.cashback.features.places.scenes.places.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.alextos.cashback.common.views.FavouriteButton
import com.alextos.cashback.core.domain.models.Place

@Composable
fun PlaceItemView(
    modifier: Modifier,
    place: Place,
    onFavouriteToggle: (Place) -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(horizontalAlignment = Alignment.Start) {
            Text(text = place.name)

            Text(text = place.category.name)
        }

        FavouriteButton(
            isFavourite = place.isFavourite,
            onFavouriteToggle = { onFavouriteToggle(place) }
        )
    }
}