package com.alextos.cashback.features.cards.domain.use_cases

import com.alextos.cashback.core.domain.models.Card
import com.alextos.cashback.core.domain.models.Category

class ValidateCashbackUseCase {
    fun execute(
        card: Card,
        percent: Double,
        selectedCategory: Category?
    ): Boolean {
        return percent > 0 && selectedCategory != null && !card.cashback.map { it.category }.contains(selectedCategory)
    }
}