package com.alextos.cashback.core.domain.models

import com.alextos.cashback.common.views.ListElement
import com.alextos.cashback.common.views.PickerElement
import com.alextos.cashback.core.domain.models.currency.Currency
import java.util.UUID
import kotlin.random.Random

data class Card(
    override val id: String = UUID.randomUUID().toString(),
    var name: String,
    val cashback: List<Cashback> = listOf(),
    var color: String? = null,
    var isArchived: Boolean = false,
    var isFavourite: Boolean = false,
    var currency: Currency = Currency.RUBLE
): ListElement, PickerElement {
    override val pickerText: String
        get() = name

    override fun toString(): String {
        if (isEmpty()) {
            return "Нет кешбэка"
        }

        return sortedCashback().joinToString(separator = ", ") { it.toString() }
    }

    fun toStringWith(searchQuery: String): String {
        val query = searchQuery.lowercase()
        return if (query.isEmpty() || isEmpty()) {
            toString()
        } else {
            sortedCashback().filter { cashback ->
                cashback.category.name.lowercase().contains(query)
                        || cashback.category.synonyms?.lowercase()?.contains(query) == true
            }.joinToString(separator = ", ") { it.toString() }
        }
    }

    fun isEmpty() = cashback.isEmpty()

    fun sortedCategories() = sortedCashback().map { it.category }

    private fun sortedCashback() = cashback.sortedBy { it.order }
}

fun generateMockCard(isEmpty: Boolean = false): Card {
    val cardNames = listOf("Gold Card", "Platinum Card", "Travel Card", "Cashback Card", "Business Card")
    val colors = listOf("#FFD700", "#C0C0C0", "#1E90FF", "#4CAF50", "#8B0000", null) // Цвета и null

    return Card(
        id = UUID.randomUUID().toString(),
        name = cardNames.random(), // Случайное название карты
        cashback = if (isEmpty) emptyList() else List(Random.nextInt(3, 5)) { generateMockCashback() },
        color = colors.random(), // Случайный цвет или null
        isArchived = false,
        isFavourite = Random.nextBoolean(),
        currency = Currency.entries.random()
    )
}