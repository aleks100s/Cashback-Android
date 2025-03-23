package com.alextos.cashback.core.di

import androidx.room.Room
import com.alextos.cashback.core.data.database.AppDatabase
import com.alextos.cashback.core.data.database.DatabaseSeeder
import com.alextos.cashback.core.data.repository.CardRepositoryImpl
import com.alextos.cashback.core.data.repository.CategoryRepositoryImpl
import com.alextos.cashback.core.data.repository.PlaceRepositoryImpl
import com.alextos.cashback.core.data.services.AnalyticsServiceImpl
import com.alextos.cashback.core.data.services.AppInfoServiceImpl
import com.alextos.cashback.core.data.settings.SettingsManagerImpl
import com.alextos.cashback.core.data.services.PasteboardServiceImpl
import com.alextos.cashback.core.data.services.ShareServiceImpl
import com.alextos.cashback.core.data.services.ToastServiceImpl
import com.alextos.cashback.core.data.services.UserDataServiceImpl
import com.alextos.cashback.core.data.services.WidgetUpdateServiceImpl
import com.alextos.cashback.core.domain.repository.CardRepository
import com.alextos.cashback.core.domain.repository.CategoryRepository
import com.alextos.cashback.core.domain.repository.PlaceRepository
import com.alextos.cashback.core.domain.services.AnalyticsService
import com.alextos.cashback.core.domain.services.AppInfoService
import com.alextos.cashback.core.domain.settings.SettingsManager
import com.alextos.cashback.core.domain.services.PasteboardService
import com.alextos.cashback.core.domain.services.ShareService
import com.alextos.cashback.core.domain.services.ToastService
import com.alextos.cashback.core.domain.services.UserDataService
import com.alextos.cashback.core.domain.services.WidgetUpdateService
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

    factory<CategoryRepository> { CategoryRepositoryImpl(get()) }
    factory<CardRepository> { CardRepositoryImpl(get(), get()) }
    factory<PlaceRepository> { PlaceRepositoryImpl(get()) }

    factory<PasteboardService> { PasteboardServiceImpl(androidApplication()) }
    factory<ToastService> { ToastServiceImpl(androidContext()) }
    factory<ShareService> { ShareServiceImpl(androidContext()) }
    factory<AppInfoService> { AppInfoServiceImpl(androidContext()) }
    single<UserDataService> { UserDataServiceImpl(androidContext(), get(), get(), get()) }
    factory<AnalyticsService> { AnalyticsServiceImpl() }
    factory<WidgetUpdateService> { WidgetUpdateServiceImpl(androidContext()) }
}