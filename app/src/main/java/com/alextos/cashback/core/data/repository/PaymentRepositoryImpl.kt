package com.alextos.cashback.core.data.repository

import com.alextos.cashback.core.data.dao.CashbackDao
import com.alextos.cashback.core.data.dao.PaymentDao
import com.alextos.cashback.core.data.entities.combined_entities.PaymentWithCard
import com.alextos.cashback.core.data.entities.mappers.toDomain
import com.alextos.cashback.core.data.entities.mappers.toEntity
import com.alextos.cashback.core.domain.models.Card
import com.alextos.cashback.core.domain.models.Payment
import com.alextos.cashback.core.domain.repository.PaymentRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import java.time.ZoneId

class PaymentRepositoryImpl(
    private val paymentDao: PaymentDao,
    private val cashbackDao: CashbackDao
): PaymentRepository {
    override fun getAllPayments(): Flow<List<Payment>> {
        return paymentDao.getAll().map { list ->
            list.map { entity ->
                constructPayment(entity)
            }
        }
    }

    override fun getPeriodPayments(from: LocalDate, to: LocalDate): Flow<List<Payment>> {
        val start = from.atStartOfDay(ZoneId.systemDefault()).toEpochSecond()
        val end = to.atStartOfDay(ZoneId.systemDefault()).toEpochSecond()
        return paymentDao.getPeriod(start, end).map { list ->
            list.map { entity ->
                constructPayment(entity)
            }
        }
    }

    override suspend fun delete(payment: Payment) {
        paymentDao.delete(payment.toEntity())
    }

    override suspend fun deleteAllPayments(card: Card) {
        paymentDao.deletePayments(card.id)
    }

    private suspend fun constructPayment(entity: PaymentWithCard): Payment {
        val cashback = cashbackDao.getAllBy(entity.card.id)
            .map { it.toDomain() }
        return entity.toDomain(cashback)
    }
}