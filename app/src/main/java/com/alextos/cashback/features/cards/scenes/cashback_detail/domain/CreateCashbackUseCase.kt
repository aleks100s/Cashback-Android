package com.alextos.cashback.features.cards.scenes.cashback_detail.domain

import com.alextos.cashback.core.domain.models.Card
import com.alextos.cashback.core.domain.models.Cashback
import com.alextos.cashback.core.domain.models.Category
import com.alextos.cashback.features.cards.domain.CardsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CreateCashbackUseCase(
    private val repository: CardsRepository
) {
    suspend fun execute(
        card: Card,
        existingCashback: Cashback?,
        percent: String,
        category: Category
    ) {
        withContext(Dispatchers.IO) {
            existingCashback?.let { oldCashback ->
                repository.deleteCashback(oldCashback, card.id)
            }
            val percent = (percent.toDoubleOrNull() ?: 0.0) / 100
            val newCashback = Cashback(
                percent = percent,
                category = category,
                order = card.cashback.count()
            )
            repository.createCashback(newCashback, card.id)
            repository.createOrUpdate(card)
        }
    }
}