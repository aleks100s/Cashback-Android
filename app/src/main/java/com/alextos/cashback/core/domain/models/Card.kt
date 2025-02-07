package com.alextos.cashback.core.domain.models

import java.util.UUID
import kotlin.random.Random

data class Card(
    val id: UUID = UUID.randomUUID(),
    var name: String,
    val cashback: List<Cashback> = listOf(),
    var color: String? = null,
    var isArchived: Boolean = false,
    var isFavourite: Boolean = false,
    var currency: String = "Рубли",
    var currencySymbol: String = "₽"
) {
    override fun toString(): String {
        if (isEmpty()) {
            return "Нет кэшбэка"
        }

        return sortedCashback().joinToString(separator = ", ") { it.toString() }
    }

    fun isEmpty() = cashback.isEmpty()

    fun sortedCategories() = sortedCashback().map { it.category }

    private fun sortedCashback() = cashback.sortedBy { it.category.name }
}

fun generateMockCard(isEmpty: Boolean = false): Card {
    val cardNames = listOf("Gold Card", "Platinum Card", "Travel Card", "Cashback Card", "Business Card")
    val colors = listOf("#FFD700", "#C0C0C0", "#1E90FF", "#4CAF50", "#8B0000", null) // Цвета и null
    val currencies = listOf("USD" to "$", "EUR" to "€", "RUB" to "₽", "GBP" to "£", "JPY" to "¥")

    val (currency, symbol) = currencies.random() // Выбираем случайную валюту

    return Card(
        id = UUID.randomUUID(),
        name = cardNames.random(), // Случайное название карты
        cashback = if (isEmpty) emptyList() else List(Random.nextInt(3, 5)) { generateMockCashback() },
        color = colors.random(), // Случайный цвет или null
        isArchived = false,
        isFavourite = Random.nextBoolean(),
        currency = currency,
        currencySymbol = symbol
    )
}