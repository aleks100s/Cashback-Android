package com.alextos.cashback.core.domain

import java.util.UUID
import kotlin.random.Random

data class Card(
    val id: UUID,
    var name: String,
    val cashback: List<Cashback>,
    var color: String?,
    var isArchived: Boolean,
    var isFavourite: Boolean,
    var currency: String,
    var currencySymbol: String
)

fun generateMockCard(): Card {
    val cardNames = listOf("Gold Card", "Platinum Card", "Travel Card", "Cashback Card", "Business Card")
    val colors = listOf("#FFD700", "#C0C0C0", "#1E90FF", "#4CAF50", "#8B0000", null) // Цвета и null
    val currencies = listOf("USD" to "$", "EUR" to "€", "RUB" to "₽", "GBP" to "£", "JPY" to "¥")

    val (currency, symbol) = currencies.random() // Выбираем случайную валюту

    return Card(
        id = UUID.randomUUID(),
        name = cardNames.random(), // Случайное название карты
        cashback = List(Random.nextInt(1, 4)) { generateMockCashback() }, // От 1 до 3 случайных кешбэков
        color = colors.random(), // Случайный цвет или null
        isArchived = Random.nextBoolean(),
        isFavourite = Random.nextBoolean(),
        currency = currency,
        currencySymbol = symbol
    )
}