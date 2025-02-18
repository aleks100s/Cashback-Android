package com.alextos.cashback.features.cards.scenes.card_detail.domain

import com.alextos.cashback.core.domain.models.Card
import com.alextos.cashback.core.domain.models.Cashback
import com.alextos.cashback.core.domain.repository.CardsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DeleteCashbackUseCase(
    private val repository: CardsRepository
) {
    suspend fun execute(
        card: Card,
        cashback: Cashback
    ) {
        withContext(Dispatchers.IO) {
            repository.deleteCashback(cashback = cashback, cardId = card.id)
            val updatedCard = card.copy(cashback = card.cashback.filter { it.id != cashback.id })
            updatedCard.cashback.forEachIndexed { index, cashback ->
                repository.createOrUpdateCashback(cashback.copy(order = index), updatedCard.id)
            }
            repository.createOrUpdate(updatedCard)
        }
    }
}