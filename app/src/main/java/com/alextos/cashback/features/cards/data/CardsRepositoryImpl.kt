package com.alextos.cashback.features.cards.data

import com.alextos.cashback.core.data.dao.CardDao
import com.alextos.cashback.core.data.dao.CashbackDao
import com.alextos.cashback.core.data.entities.CardEntity
import com.alextos.cashback.core.data.mappers.toDomain
import com.alextos.cashback.core.data.mappers.toEntity
import com.alextos.cashback.core.domain.models.Card
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

    override fun update(card: Card) {
        cardDao.insert(card.toEntity())
    }

    override fun getCard(id: String): Flow<Card?> {
        return cardDao.getCard(id)
            .mapNotNull { it.firstOrNull() }
            .map { constructCard(it) }
    }

    private suspend fun constructCard(entity: CardEntity): Card {
        val cashback = cashbackDao.getAllBy(entity.id)
            .map { it.toDomain() }
        return entity.toDomain(cashback)
    }
}