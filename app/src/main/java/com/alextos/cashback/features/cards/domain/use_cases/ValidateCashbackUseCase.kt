package com.alextos.cashback.features.cards.domain.use_cases

import com.alextos.cashback.core.domain.models.Card
import com.alextos.cashback.core.domain.models.Category

class ValidateCashbackUseCase {
    fun execute(
        card: Card,
        percent: Double,
        selectedCategory: Category?,
        initialCategory: Category?
    ): Boolean {
        val cardHasSameCategory = card.cashback.map { it.category }.contains(selectedCategory)
        return (!cardHasSameCategory || initialCategory == selectedCategory)
                && percent > 0
                && selectedCategory != null
    }
}