package com.alextos.cashback.features.settings.scenes.card_trashbin.data

import com.alextos.cashback.core.data.dao.CardDao
import com.alextos.cashback.core.data.entities.mappers.toDomain
import com.alextos.cashback.core.data.entities.mappers.toEntity
import com.alextos.cashback.core.domain.models.Card
import com.alextos.cashback.features.settings.scenes.card_trashbin.domain.CardTrashbinRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class CardTrashbinRepositoryImpl(
    private val cardDao: CardDao
): CardTrashbinRepository {
    override fun getArchivedCards(): Flow<List<Card>> {
        return cardDao.getAllArchived()
            .map { list ->
                list.map { it.toDomain(emptyList()) }
            }
    }

    override suspend fun unarchive(card: Card) {
        withContext(Dispatchers.IO) {
            cardDao.upsert(card.copy(isArchived = false).toEntity())
        }
    }
}