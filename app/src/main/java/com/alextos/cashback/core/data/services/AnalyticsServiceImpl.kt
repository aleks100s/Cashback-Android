package com.alextos.cashback.core.data.services

import com.alextos.cashback.BuildConfig
import com.alextos.cashback.core.domain.services.AnalyticsEvent
import com.alextos.cashback.core.domain.services.AnalyticsService
import io.appmetrica.analytics.AppMetrica

class AnalyticsServiceImpl: AnalyticsService {
    override fun logEvent(event: AnalyticsEvent) {
        if (BuildConfig.DEBUG) {
            return
        }
        AppMetrica.reportEvent(event.rawValue)
    }
}