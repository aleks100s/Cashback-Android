package com.alextos.cashback.core.domain.models

import java.util.Locale
import java.util.UUID
import kotlin.random.Random

data class Cashback(
    val id: UUID,
    val category: Category,
    val percent: Double
) {
    override fun toString(): String {
        return if (showFloatingPoint()) {
            String.format(locale = Locale.forLanguageTag("ru_RU"), "%s %.0f%%", category.name, percent * 100)
        } else {
            String.format(locale = Locale.forLanguageTag("ru_RU"), "%s %.1f%%", category.name, percent * 100)
        }
    }

    fun percentString(): String {
        return if (showFloatingPoint()) {
            String.format(locale = Locale.forLanguageTag("ru_RU"), "%.0f%%", percent * 100)
        } else {
            String.format(locale = Locale.forLanguageTag("ru_RU"), "%.1f%%", percent * 100)
        }
    }

    private fun showFloatingPoint(): Boolean {
        return (percent * 100) % 10 < 0.1
    }
}

fun generateMockCashback(category: Category? = null): Cashback {
    return Cashback(
        id = UUID.randomUUID(),
        category = category ?: generateMockCategory(), // Генерируем случайную категорию
        percent = Random.nextDouble(0.01, 0.2) // Генерируем процент от 1.0 до 20.0
    )
}