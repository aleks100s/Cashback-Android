package com.alextos.cashback.core.di

import androidx.room.Room
import com.alextos.cashback.core.data.database.AppDatabase
import com.alextos.cashback.core.data.database.DatabaseSeeder
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

    factory { get<AppDatabase>().categoryDao() }
    factory { get<AppDatabase>().placeDao() }
    factory { get<AppDatabase>().cashbackDao() }
    factory { get<AppDatabase>().cardDao() }
    factory { get<AppDatabase>().paymentDao() }
}