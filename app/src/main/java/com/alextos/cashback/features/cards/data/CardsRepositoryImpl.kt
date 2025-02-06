package com.alextos.cashback.features.cards.data

import com.alextos.cashback.core.data.dao.CardDao
import com.alextos.cashback.core.data.dao.CashbackDao
import com.alextos.cashback.core.data.mappers.toDomain
import com.alextos.cashback.core.data.mappers.toEntity
import com.alextos.cashback.core.domain.models.Card
import com.alextos.cashback.features.cards.domain.CardsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CardsRepositoryImpl(
    private val cardDao: CardDao,
    private val cashbackDao: CashbackDao
): CardsRepository {
    override fun getAllCards(): Flow<List<Card>> {
        return cardDao.getAll().map { list ->
            list.map { card ->
                val cashback = cashbackDao.getAllBy(card.id)
                    .map { it.toDomain() }
                card.toDomain(cashback = cashback)
            }
        }
    }

    override fun update(card: Card) {
        cardDao.insert(card.toEntity())
    }
}