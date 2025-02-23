package com.alextos.cashback.core.data.repository

import com.alextos.cashback.core.data.dao.CardDao
import com.alextos.cashback.core.data.dao.CashbackDao
import com.alextos.cashback.core.data.entities.CardEntity
import com.alextos.cashback.core.data.entities.mappers.toDomain
import com.alextos.cashback.core.data.entities.mappers.toEntity
import com.alextos.cashback.core.domain.models.Card
import com.alextos.cashback.core.domain.models.Cashback
import com.alextos.cashback.core.domain.repository.CardsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull

class CardsRepositoryImpl(
    private val cardDao: CardDao,
    private val cashbackDao: CashbackDao
): CardsRepository {
    override fun getAllCards(): Flow<List<Card>> {
        return cardDao.getAllUnarchived().map { list ->
            list.map { constructCard(it) }
        }
    }

    override suspend fun createOrUpdate(card: Card) {
        cardDao.upsert(card.toEntity())
    }

    override fun getCardFlow(id: String): Flow<Card?> {
        return cardDao.getCardFlow(id)
            .mapNotNull { it.firstOrNull() }
            .map { constructCard(it) }
    }

    override suspend fun getCard(id: String): Card? {
        return cardDao.getCard(id).firstOrNull()?.let {
            constructCard(it)
        }
    }

    override suspend fun deleteCashback(cashback: Cashback, cardId: String) {
        cashbackDao.delete(cashback.toEntity(cardId))
    }

    override suspend fun createOrUpdateCashback(cashback: Cashback, cardId: String) {
        cashbackDao.upsert(cashback.toEntity(cardId))
    }

    override suspend fun getCashback(id: String): Cashback? {
        return cashbackDao.getCashback(id).firstOrNull()?.toDomain()
    }

    override suspend fun archiveCard(card: Card) {
        cardDao.upsert(card.toEntity())
    }

    private suspend fun constructCard(entity: CardEntity): Card {
        val cashback = cashbackDao.getAllBy(entity.id)
            .map { it.toDomain() }
        return entity.toDomain(cashback)
    }
}