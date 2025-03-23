package com.alextos.cashback.features.places.scenes.places.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.alextos.cashback.common.views.FavouriteButton
import com.alextos.cashback.core.domain.models.Place

@Composable
fun PlaceItemView(
    modifier: Modifier,
    place: Place,
    onFavouriteToggle: (Place) -> Unit
) {
    val color = MaterialTheme.colorScheme.primary
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            color.copy(red = color.red * 0.75f, green = color.green * 0.75f, blue = color.blue * 0.75f),
                            color.copy(red = color.red * 0.5f, green = color.green * 0.5f, blue = color.blue * 0.5f)
                        )
                    ),
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = place.name.uppercase().firstOrNull().toString(),
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        }

        Column(horizontalAlignment = Alignment.Start) {
            Text(text = place.name)

            Text(text = place.category.name, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f))
        }

        Spacer(modifier = Modifier.fillMaxWidth())

        FavouriteButton(
            isFavourite = place.isFavourite,
            onFavouriteToggle = { onFavouriteToggle(place) }
        )
    }
}