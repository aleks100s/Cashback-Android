package com.alextos.cashback.app.widget.views

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.action.clickable
import androidx.glance.appwidget.action.actionRunCallback
import androidx.glance.appwidget.lazy.LazyColumn
import androidx.glance.appwidget.lazy.items
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.padding
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.ColorFilter
import androidx.glance.layout.size
import androidx.glance.unit.ColorProvider
import com.alextos.cashback.R
import com.alextos.cashback.app.widget.WidgetRefreshAction
import com.alextos.cashback.core.domain.models.Card

@Composable
fun WidgetCardListView(
    context: Context,
    cards: List<Card>
) {
    if (cards.isEmpty()) {
        Box(
            modifier = GlanceModifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = context.getString(R.string.widget_no_card),
                style = TextStyle(color = GlanceTheme.colors.primary)
            )
        }
    } else {
        Column(
            modifier = GlanceModifier
                .padding(top = 8.dp)
                .fillMaxWidth()
        ) {
            Row(GlanceModifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Text(
                    modifier = GlanceModifier.padding(8.dp),
                    text = context.getString(R.string.widget_favourite_cards),
                    style = TextStyle(
                        color = GlanceTheme.colors.primary,
                        fontSize = TextUnit(20f, TextUnitType.Sp),
                        fontWeight = FontWeight.Bold
                    )
                )

                Spacer(modifier = GlanceModifier.defaultWeight())

                Image(
                    provider = ImageProvider(R.drawable.refresh),
                    modifier = GlanceModifier.clickable(
                        onClick = actionRunCallback<WidgetRefreshAction>()
                    ).size(24.dp),
                    contentDescription = context.getString(R.string.widget_refresh),
                    colorFilter = ColorFilter.tint(GlanceTheme.colors.primary)
                )
            }


            LazyColumn {
                items(cards) { card ->
                    Column(modifier = GlanceModifier.fillMaxWidth()) {
                        WidgetCardItemView(card = card)

                        if (card != cards.lastOrNull()) {
                            Spacer(modifier = GlanceModifier.padding(4.dp))
                        }
                    }
                }
            }
        }
    }
}