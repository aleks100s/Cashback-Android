package com.alextos.cashback.core.domain.repository

import com.alextos.cashback.core.domain.models.Card
import com.alextos.cashback.core.domain.models.Payment
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface PaymentRepository {
    suspend fun getAllPayments(): List<Payment>
    suspend fun getPeriodPayments(from: LocalDate, to: LocalDate): List<Payment>
    suspend fun delete(payment: Payment)
    suspend fun deleteAllPayments(card: Card)
}