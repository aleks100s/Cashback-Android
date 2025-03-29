package com.alextos.cashback.core.domain.repository

import com.alextos.cashback.core.domain.models.Payment
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface PaymentRepository {
    fun getAllPayments(): Flow<List<Payment>>
    fun getPeriodPayments(from: LocalDate, to: LocalDate): Flow<List<Payment>>
    suspend fun delete(payment: Payment)
}