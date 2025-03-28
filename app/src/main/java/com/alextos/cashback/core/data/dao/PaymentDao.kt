package com.alextos.cashback.core.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.alextos.cashback.core.data.entities.combined_entities.PaymentWithCard
import com.alextos.cashback.core.data.entities.PaymentEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PaymentDao {
    @Upsert
    suspend fun insert(payment: PaymentEntity)

    @Transaction
    @Query("SELECT * FROM payments")
    fun getAll(): Flow<List<PaymentWithCard>>

    @Query("SELECT * FROM payments WHERE date > :from AND date < :to")
    fun getPeriod(from: Long, to: Long): Flow<List<PaymentWithCard>>
}