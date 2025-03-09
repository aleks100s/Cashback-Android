package com.alextos.cashback.app

import android.app.Application
import com.alextos.cashback.core.di.coreModule
import com.alextos.cashback.features.cards.di.cardsModule
import com.alextos.cashback.features.category.di.categoryModule
import com.alextos.cashback.features.settings.di.settingsModule
import io.appmetrica.analytics.AppMetrica
import io.appmetrica.analytics.AppMetricaConfig
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class CashbackApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        AppMetrica.activate(this, AppMetricaConfig.newConfigBuilder("d3b3d6f5-c58f-4b1f-b4cd-debdeb274ffd").build())
        startKoin {
            androidContext(this@CashbackApplication)
            modules(appModule, coreModule, categoryModule, cardsModule, settingsModule)
        }
    }
}