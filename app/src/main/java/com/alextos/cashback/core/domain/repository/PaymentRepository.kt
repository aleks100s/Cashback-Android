package com.alextos.cashback.core.domain.repository

import com.alextos.cashback.core.domain.models.Card
import com.alextos.cashback.core.domain.models.Payment
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface PaymentRepository {
    fun getAllPaymentsFlow(): Flow<List<Payment>>
    fun getPeriodPayments(from: LocalDate, to: LocalDate): Flow<List<Payment>>
    suspend fun delete(payment: Payment)
    suspend fun deleteAllPayments(card: Card)
    suspend fun replaceAll(payments: List<Payment>)
    suspend fun getAllPayments(): List<Payment>
    suspend fun save(payment: Payment)
    fun getPayment(id: String): Flow<Payment?>
}