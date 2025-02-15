package com.alextos.cashback.core.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.alextos.cashback.core.data.entities.combined_entities.CashbackWithCategory
import com.alextos.cashback.core.data.entities.CashbackEntity

@Dao
interface CashbackDao {
    @Transaction
    @Query("SELECT * FROM cashback WHERE cardId = :cardId ORDER BY `order`")
    suspend fun getAllBy(cardId: String): List<CashbackWithCategory>

    @Query("SELECT * FROM cashback WHERE id = :cashbackId LIMIT 1")
    suspend fun getCashback(cashbackId: String): List<CashbackWithCategory>

    @Insert
    suspend fun insert(cashback: CashbackEntity)

    @Delete
    suspend fun delete(cashback: CashbackEntity)
}