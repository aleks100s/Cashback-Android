package com.alextos.cashback.features.cards.scenes.card_detail.domain

import com.alextos.cashback.core.domain.models.Card
import com.alextos.cashback.core.domain.repository.CardRepository

class DeleteCardUseCase(
    private val repository: CardRepository
) {
    suspend fun execute(card: Card) {
        card.cashback.forEach {
            repository.deleteCashback(it, card.id)
        }
        repository.archiveCard(card.copy(isArchived = true))
    }
}