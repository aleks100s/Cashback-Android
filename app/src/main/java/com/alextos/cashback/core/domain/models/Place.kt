package com.alextos.cashback.core.domain.models

import java.util.UUID
import kotlin.random.Random

data class Place(
    val id: UUID,
    val name: String,
    val category: Category,
    val isFavourite: Boolean
)


fun generateMockPlace(category: Category? = null): Place {
    val randomNames = listOf("Cafe Mocha", "Burger Spot", "Tech Hub", "Zen Garden", "Book Haven")

    return Place(
        id = UUID.randomUUID(),
        name = randomNames.random(),
        category = category ?: generateMockCategory(),
        isFavourite = Random.nextBoolean()
    )
}