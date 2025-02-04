package com.alextos.cashback.core.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.alextos.cashback.core.data.dao.CategoryDao
import com.alextos.cashback.core.data.entities.CategoryEntity

@Database(entities = [CategoryEntity::class], version = 1)
abstract class Database : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao

    companion object {
        const val DB_NAME = "cashback-database"
    }
}