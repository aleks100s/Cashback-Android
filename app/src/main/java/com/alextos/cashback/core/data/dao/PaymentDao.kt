package com.alextos.cashback.core.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.alextos.cashback.core.data.entities.CardEntity
import com.alextos.cashback.core.data.entities.combined_entities.PaymentWithCard
import com.alextos.cashback.core.data.entities.PaymentEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PaymentDao {
    @Upsert
    suspend fun insert(payment: PaymentEntity)

    @Transaction
    @Query("SELECT * FROM payments")
    suspend fun getAll(): List<PaymentWithCard>

    @Query("SELECT * FROM payments WHERE date > :from AND date < :to")
    suspend fun getPeriod(from: Long, to: Long): List<PaymentWithCard>

    @Delete
    suspend fun delete(payment: PaymentEntity)

    @Query("DELETE FROM payments WHERE cardId = :cardId")
    suspend fun deletePayments(cardId: String)
}