package com.alextos.cashback.features.settings.scenes.card_trashbin.domain

import com.alextos.cashback.core.domain.models.Card
import kotlinx.coroutines.flow.Flow

interface CardTrashbinRepository {
    fun getArchivedCards(): Flow<List<Card>>
    suspend fun unarchive(card: Card)
}