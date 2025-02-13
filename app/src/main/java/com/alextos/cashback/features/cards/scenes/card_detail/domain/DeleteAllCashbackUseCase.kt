package com.alextos.cashback.features.cards.scenes.card_detail.domain

import com.alextos.cashback.core.domain.models.Card
import com.alextos.cashback.features.cards.domain.CardsRepository

class DeleteAllCashbackUseCase(
    private val repository: CardsRepository
) {
    suspend fun execute(card: Card) {
        card.cashback.forEach {
            repository.deleteCashback(it, card.id)
        }
        repository.createOrUpdate(card)
    }
}