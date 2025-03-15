package com.alextos.cashback.widget

import android.content.Context
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
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
import androidx.glance.layout.Column
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.padding
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import com.alextos.cashback.R
import com.alextos.cashback.core.domain.models.Card
import com.alextos.cashback.core.domain.repository.CardRepository
import com.alextos.cashback.widget.views.CardItemView
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
                        Column(
                            modifier = GlanceModifier
                                .padding(top = 8.dp)
                                .fillMaxWidth()
                        ) {
                            Text(
                                modifier = GlanceModifier.padding(8.dp),
                                text = context.getString(R.string.widget_favourite_cards),
                                style = TextStyle(
                                    color = GlanceTheme.colors.primary,
                                    fontSize = TextUnit(20f, TextUnitType.Sp),
                                    fontWeight = FontWeight.Bold
                                )
                            )

                            LazyColumn {
                                items(cards) { card ->
                                    Column(modifier = GlanceModifier.fillMaxWidth()) {
                                        CardItemView(card = card)

                                        if (card != cards.lastOrNull()) {
                                            Spacer(modifier = GlanceModifier.padding(4.dp))
                                        }
                                    }
                                }
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