package com.alextos.cashback.widget

import android.content.Context
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.components.Scaffold
import androidx.glance.appwidget.lazy.LazyColumn
import androidx.glance.appwidget.lazy.items
import androidx.glance.appwidget.provideContent
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.padding
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import com.alextos.cashback.R
import com.alextos.cashback.core.domain.models.Card
import com.alextos.cashback.core.domain.repository.CardRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.java.KoinJavaComponent.inject

class FavouriteCardsWidget : GlanceAppWidget() {
    private val repository: CardRepository by inject(CardRepository::class.java)

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        val cards = getFavouriteCard()
        provideContent {
            GlanceTheme {
                Scaffold {
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
                        LazyColumn(modifier = GlanceModifier.padding(16.dp)) {
                            items(cards) { card ->
                                Text(
                                    text = card.name,
                                    style = TextStyle(color = GlanceTheme.colors.primary),
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    private suspend fun getFavouriteCard(): List<Card> {
        return withContext(Dispatchers.IO) {
            repository.getFavouriteCards()
        }
    }
}