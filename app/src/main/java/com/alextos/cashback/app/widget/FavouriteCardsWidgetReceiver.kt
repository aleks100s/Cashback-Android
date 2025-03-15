package com.alextos.cashback.app.widget

import android.appwidget.AppWidgetManager
import android.content.Context
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavouriteCardsWidgetReceiver: GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget = FavouriteCardsWidget()

    private val scope = CoroutineScope(Dispatchers.IO)

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)
        scope.launch {
            updateWidgetData(context)
        }
    }

    private suspend fun updateWidgetData(context: Context) {
        val glanceId = GlanceAppWidgetManager(context)
            .getGlanceIds(FavouriteCardsWidget::class.java)
            .firstOrNull()

        glanceId?.let {
            glanceAppWidget.update(context, it)
        }
    }
}