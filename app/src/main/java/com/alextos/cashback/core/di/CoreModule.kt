package com.alextos.cashback.core.di

import androidx.room.Room
import com.alextos.cashback.core.data.database.Database
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val coreModule = module {
    single {
        Room.databaseBuilder(
            androidApplication(),
            Database::class.java, Database.DB_NAME
        ).build()
    }

    factory { get<Database>().categoryDao() }
}