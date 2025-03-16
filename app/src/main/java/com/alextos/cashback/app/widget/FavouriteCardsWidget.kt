package com.alextos.cashback.app.widget

import android.content.Context
import androidx.glance.GlanceId
import androidx.glance.GlanceTheme
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.components.Scaffold
import androidx.glance.appwidget.provideContent
import com.alextos.cashback.core.domain.models.Card
import com.alextos.cashback.core.domain.repository.CardRepository
import com.alextos.cashback.app.widget.views.WidgetCardListView
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
                    WidgetCardListView(context, cards)
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