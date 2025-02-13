package com.alextos.cashback.features.cards.scenes.cashback_detail.domain

import com.alextos.cashback.core.domain.models.Card
import com.alextos.cashback.core.domain.models.Category
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ValidateCashbackUseCase {
    suspend fun execute(
        card: Card,
        percent: Double,
        selectedCategory: Category?,
        initialCategory: Category?
    ): Boolean {
        return withContext(Dispatchers.IO) {
            val cardHasSameCategory =
                card.cashback.map { it.category.id }.contains(selectedCategory?.id)
            return@withContext ((!cardHasSameCategory || initialCategory == selectedCategory)
                    && percent > 0
                    && selectedCategory != null)
        }
    }
}