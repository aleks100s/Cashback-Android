package com.alextos.cashback.features.cards.scenes.card_detail.domain

import com.alextos.cashback.core.domain.models.Card
import com.alextos.cashback.features.cards.domain.CardsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DeleteCardUseCase(
    private val repository: CardsRepository
) {
    suspend fun execute(card: Card) {
        withContext(Dispatchers.IO) {
            card.cashback.forEach {
                repository.deleteCashback(it, card.id)
            }
            repository.archiveCard(card.copy(isArchived = true))
        }
    }
}