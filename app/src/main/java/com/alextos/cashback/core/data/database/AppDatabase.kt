package com.alextos.cashback.core.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.alextos.cashback.core.data.dao.CardDao
import com.alextos.cashback.core.data.dao.CashbackDao
import com.alextos.cashback.core.data.dao.CategoryDao
import com.alextos.cashback.core.data.dao.PaymentDao
import com.alextos.cashback.core.data.dao.PlaceDao
import com.alextos.cashback.core.data.entities.CardEntity
import com.alextos.cashback.core.data.entities.CashbackEntity
import com.alextos.cashback.core.data.entities.CategoryEntity
import com.alextos.cashback.core.data.entities.PaymentEntity
import com.alextos.cashback.core.data.entities.PlaceEntity

@Database(
    entities = [CategoryEntity::class,
                PlaceEntity::class,
                CashbackEntity::class,
                CardEntity::class,
                PaymentEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao
    abstract fun placeDao(): PlaceDao
    abstract fun cashbackDao(): CashbackDao
    abstract fun cardDao(): CardDao
    abstract fun paymentDao(): PaymentDao

    companion object {
        const val DB_NAME = "cashback-database"
    }
}