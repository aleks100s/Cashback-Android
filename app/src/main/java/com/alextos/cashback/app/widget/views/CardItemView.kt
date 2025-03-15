package com.alextos.cashback.app.widget.views

import androidx.glance.layout.Box
import androidx.glance.layout.Column
import androidx.glance.text.Text
import androidx.compose.runtime.Composable
import androidx.glance.layout.Alignment
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.appwidget.cornerRadius
import androidx.glance.background
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.padding
import androidx.glance.text.FontWeight
import androidx.glance.text.TextStyle
import com.alextos.cashback.common.makeColor
import com.alextos.cashback.core.domain.models.Card

@Composable
fun CardItemView(
    modifier: GlanceModifier = GlanceModifier,
    card: Card
) {
    Box(
        modifier = modifier
            .cornerRadius(16.dp)
            .background(color = makeColor(card.color).copy(0.4f))
            .fillMaxWidth()
    ) {
        Column(
            modifier = GlanceModifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.Start,
        ) {
            Text(
                modifier = GlanceModifier.padding(bottom = 8.dp),
                text = card.name,
                style = TextStyle(
                    color = GlanceTheme.colors.onSurface,
                    fontWeight = FontWeight.Bold
                ),
            )

            Text(
                text = card.toString(),
                style = TextStyle(color = GlanceTheme.colors.onSurface)
            )
        }
    }
}