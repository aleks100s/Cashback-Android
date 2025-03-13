package com.alextos.cashback.core.domain.repository

import com.alextos.cashback.core.domain.models.Card
import com.alextos.cashback.core.domain.models.Cashback
import kotlinx.coroutines.flow.Flow

interface CardRepository {
    fun getAllCardsFlow(): Flow<List<Card>>
    suspend fun createOrUpdate(card: Card)
    fun getCardFlow(id: String): Flow<Card?>
    suspend fun getCard(id: String): Card?
    suspend fun deleteCashback(cashback: Cashback, cardId: String)
    suspend fun createOrUpdateCashback(cashback: Cashback, cardId: String)
    suspend fun getCashback(id: String): Cashback?
    suspend fun archiveCard(card: Card)
    suspend fun getAllCards(): List<Card>
    fun getArchivedCards(): Flow<List<Card>>
    suspend fun unarchive(card: Card)
    suspend fun replaceAll(cards: List<Card>)
    suspend fun getFavouriteCards(): List<Card>
}