package com.alextos.cashback.features.cards.presentation.add_cashback

import com.alextos.cashback.core.domain.models.Category

data class AddCashbackState(
    val percent: Double = 0.05,
    val selectedCategory: Category? = null,
    val percentOptions: List<Double> = listOf(0.01, 0.03, 0.05, 0.1)
) {
    fun isVaild(): Boolean {
        return percent > 0 && selectedCategory != null
    }
}
