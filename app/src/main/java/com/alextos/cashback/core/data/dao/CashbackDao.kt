package com.alextos.cashback.core.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.alextos.cashback.core.data.combined_entities.CashbackWithCategory
import com.alextos.cashback.core.data.entities.CashbackEntity

@Dao
interface CashbackDao {
    @Transaction
    @Query("SELECT * FROM cashback WHERE cardId = :cardId")
    suspend fun getAllBy(cardId: String): List<CashbackWithCategory>

    @Insert
    fun insert(cashback: CashbackEntity)

    @Delete
    fun delete(cashback: CashbackEntity)
}