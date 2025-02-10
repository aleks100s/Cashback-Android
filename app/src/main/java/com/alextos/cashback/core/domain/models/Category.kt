package com.alextos.cashback.core.domain.models

import com.alextos.cashback.util.views.ListElement
import java.util.UUID
import kotlin.random.Random

data class Category(
    override val id: String = UUID.randomUUID().toString(),
    val name: String,
    val emoji: String,
    val synonyms: String? = null,
    var priority: Int = 0,
    var isArchived: Boolean = false,
    val info: String?,
    val isNative: Boolean
): ListElement

fun generateMockCategory(): Category {
    val names = listOf("Books", "Music", "Cinema", "Fitness", "Pharmacy", "Electronics", "Restaurants")
    val emojis = listOf("📚", "🎵", "🎬", "🏃‍♂️", "💊", "📱", "🍽️")
    val synonymsList = listOf("Literature, Reading", "Songs, Audio", "Movies, Theater", "Gym, Sport", "Medicine, Health", "Gadgets, Tech", "Food, Dining", null)

    val index = Random.nextInt(names.size) // Выбираем случайный индекс

    return Category(
        id = UUID.randomUUID().toString(),
        name = names[index],
        emoji = emojis[index],
        synonyms = synonymsList[index],
        priority = Random.nextInt(1, 101), // Генерируем случайный приоритет от 1 до 100
        isArchived = false,
        info = null,
        isNative = true
    )
}
