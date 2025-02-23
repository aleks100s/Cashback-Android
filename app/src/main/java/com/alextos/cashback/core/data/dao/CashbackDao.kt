package com.alextos.cashback.core.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import com.alextos.cashback.core.data.entities.combined_entities.CashbackWithCategory
import com.alextos.cashback.core.data.entities.CashbackEntity

@Dao
interface CashbackDao {
    @Query("SELECT * FROM cashback WHERE cardId = :cardId ORDER BY `order`")
    suspend fun getAllBy(cardId: String): List<CashbackWithCategory>

    @Query("SELECT * FROM cashback WHERE id = :cashbackId LIMIT 1")
    suspend fun getCashback(cashbackId: String): List<CashbackWithCategory>

    @Upsert
    suspend fun upsert(cashback: CashbackEntity)

    @Delete
    suspend fun delete(cashback: CashbackEntity)

    @Insert
    suspend fun insertAll(cashback: List<CashbackEntity>)
}