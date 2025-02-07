package com.alextos.cashback.features.cards.data

import com.alextos.cashback.core.data.dao.CardDao
import com.alextos.cashback.core.data.dao.CashbackDao
import com.alextos.cashback.core.data.entities.CardEntity
import com.alextos.cashback.core.data.mappers.toDomain
import com.alextos.cashback.core.data.mappers.toEntity
import com.alextos.cashback.core.domain.models.Card
import com.alextos.cashback.core.domain.models.Cashback
import com.alextos.cashback.features.cards.domain.CardsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull

class CardsRepositoryImpl(
    private val cardDao: CardDao,
    private val cashbackDao: CashbackDao
): CardsRepository {
    override fun getAllCards(): Flow<List<Card>> {
        return cardDao.getAll().map { list ->
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

    override suspend fun delete(cashback: Cashback, cardId: String) {
        cashbackDao.delete(cashback.toEntity(cardId))
    }

    private suspend fun constructCard(entity: CardEntity): Card {
        val cashback = cashbackDao.getAllBy(entity.id)
            .map { it.toDomain() }
        return entity.toDomain(cashback)
    }
}