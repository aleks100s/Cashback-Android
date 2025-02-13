package com.alextos.cashback.core.di

import androidx.room.Room
import com.alextos.cashback.core.data.database.AppDatabase
import com.alextos.cashback.core.data.database.DatabaseSeeder
import com.alextos.cashback.core.data.settings.SettingsManagerImpl
import com.alextos.cashback.core.data.services.PasteboardServiceImpl
import com.alextos.cashback.core.data.services.ToastServiceImpl
import com.alextos.cashback.core.domain.settings.SettingsManager
import com.alextos.cashback.core.domain.services.PasteboardService
import com.alextos.cashback.core.domain.services.ToastService
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val coreModule = module {
    single {
        Room.databaseBuilder(
            androidApplication(),
            AppDatabase::class.java, AppDatabase.DB_NAME
        )
            .addCallback(DatabaseSeeder(androidContext()))
            .build()
    }

    single<SettingsManager> { SettingsManagerImpl(androidContext()) }

    factory { get<AppDatabase>().categoryDao() }
    factory { get<AppDatabase>().placeDao() }
    factory { get<AppDatabase>().cashbackDao() }
    factory { get<AppDatabase>().cardDao() }
    factory { get<AppDatabase>().paymentDao() }

    factory<PasteboardService> { PasteboardServiceImpl(androidApplication()) }
    factory<ToastService> { ToastServiceImpl(androidContext()) }
}