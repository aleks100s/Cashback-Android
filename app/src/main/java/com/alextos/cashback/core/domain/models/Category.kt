package com.alextos.cashback.core.domain.models

import java.util.UUID
import kotlin.random.Random

data class Category(
    val id: UUID,
    val name: String,
    val emoji: String,
    val synonyms: String?,
    val priority: Int,
    val isArchived: Boolean
)

fun generateMockCategory(): Category {
    val names = listOf("Books", "Music", "Cinema", "Fitness", "Pharmacy", "Electronics", "Restaurants")
    val emojis = listOf("📚", "🎵", "🎬", "🏃‍♂️", "💊", "📱", "🍽️")
    val synonymsList = listOf("Literature, Reading", "Songs, Audio", "Movies, Theater", "Gym, Sport", "Medicine, Health", "Gadgets, Tech", "Food, Dining", null)

    val index = Random.nextInt(names.size) // Выбираем случайный индекс

    return Category(
        id = UUID.randomUUID(),
        name = names[index],
        emoji = emojis[index],
        synonyms = synonymsList[index],
        priority = Random.nextInt(1, 101), // Генерируем случайный приоритет от 1 до 100
        isArchived = false
    )
}
