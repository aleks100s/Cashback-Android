package com.alextos.cashback.features.cards.domain

import com.alextos.cashback.core.domain.Card
import kotlinx.coroutines.flow.Flow

interface CardsRepository {
    fun getAllCards(): Flow<List<Card>>
    fun update(card: Card)
}