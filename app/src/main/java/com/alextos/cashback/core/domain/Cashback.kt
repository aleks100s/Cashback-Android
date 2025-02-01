package com.alextos.cashback.core.domain

import java.util.UUID
import kotlin.random.Random

data class Cashback(
    val id: UUID,
    val category: Category,
    val percent: Double
)

fun generateMockCashback(): Cashback {
    return Cashback(
        id = UUID.randomUUID(),
        category = generateMockCategory(), // Генерируем случайную категорию
        percent = Random.nextDouble(1.0, 20.0) // Генерируем процент от 1.0 до 20.0
    )
}