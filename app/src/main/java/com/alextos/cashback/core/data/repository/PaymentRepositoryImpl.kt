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
    override fun getAllPaymentsFlow(): Flow<List<Payment>> {
        return paymentDao.getAllFlow().map { list ->
            list.map { entity ->
                constructPayment(entity)
            }
        }
    }

    override suspend fun getPeriodPayments(from: LocalDate, to: LocalDate): List<Payment> {
        val start = from.atStartOfDay(ZoneId.systemDefault()).toEpochSecond()
        val end = to.atStartOfDay(ZoneId.systemDefault()).toEpochSecond()
        return paymentDao.getPeriod(start, end).map { entity ->
            constructPayment(entity)
        }
    }

    override suspend fun delete(payment: Payment) {
        paymentDao.delete(payment.toEntity())
    }

    override suspend fun deleteAllPayments(card: Card) {
        paymentDao.deletePayments(card.id)
    }

    override suspend fun replaceAll(payments: List<Payment>) {
        paymentDao.replaceAll(payments.map { it.toEntity() })
    }

    override suspend fun getAllPayments(): List<Payment> {
        return paymentDao.getAll().map { constructPayment(it) }
    }

    override suspend fun save(payment: Payment) {
        paymentDao.upsert(payment.toEntity())
    }

    override fun getPayment(id: String): Flow<Payment?> {
        return paymentDao.getPayment(id).map { list ->
            list.map { constructPayment(it) }.firstOrNull()
        }
    }

    private suspend fun constructPayment(entity: PaymentWithCard): Payment {
        val cashback = cashbackDao.getAllBy(entity.card.id)
            .map { it.toDomain() }
        return entity.toDomain(cashback)
    }
}