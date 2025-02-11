package com.alextos.cashback.features.cards.domain

import com.alextos.cashback.core.domain.models.Card
import com.alextos.cashback.core.domain.models.Cashback
import kotlinx.coroutines.flow.Flow

interface CardsRepository {
    fun getAllCards(): Flow<List<Card>>
    suspend fun createOrUpdate(card: Card)
    fun getCardFlow(id: String): Flow<Card?>
    suspend fun getCard(id: String): Card?
    suspend fun deleteCashback(cashback: Cashback, cardId: String)
    suspend fun createCashback(cashback: Cashback, cardId: String)
    suspend fun getCashback(id: String): Cashback?
    suspend fun archiveCard(card: Card)
}