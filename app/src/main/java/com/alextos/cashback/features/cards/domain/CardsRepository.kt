package com.alextos.cashback.features.cards.domain

import com.alextos.cashback.core.domain.models.Card
import kotlinx.coroutines.flow.Flow

interface CardsRepository {
    fun getAllCards(): Flow<List<Card>>
    fun update(card: Card)
    fun getCard(id: String): Flow<Card?>
}