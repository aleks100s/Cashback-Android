package com.alextos.cashback.app

import android.app.Application
import com.alextos.cashback.core.di.coreModule
import com.alextos.cashback.features.cards.di.cardsModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class CashbackApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@CashbackApplication)
            modules(appModule, coreModule, cardsModule)
        }
    }
}