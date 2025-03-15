package com.alextos.cashback.core.data.services

import android.content.Context
import android.content.Intent
import androidx.glance.appwidget.GlanceAppWidgetManager
import com.alextos.cashback.core.AppConstants
import com.alextos.cashback.core.domain.services.WidgetUpdateService
import com.alextos.cashback.app.widget.FavouriteCardsWidget
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WidgetUpdateServiceImpl(
    private val context: Context
): WidgetUpdateService {
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    override fun updateWidget() {
        coroutineScope.launch {
            forceUpdateWidget(context)
        }
    }

    private suspend fun forceUpdateWidget(context: Context) {
        val glanceManager = GlanceAppWidgetManager(context)
        val glanceIds = glanceManager.getGlanceIds(FavouriteCardsWidget::class.java)

        glanceIds.forEach { glanceId ->
            FavouriteCardsWidget().update(context, glanceId)
        }
    }
}