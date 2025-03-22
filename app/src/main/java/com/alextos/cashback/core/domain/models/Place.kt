package com.alextos.cashback.core.domain.models

import com.alextos.cashback.common.views.ListElement
import java.util.UUID
import kotlin.random.Random

data class Place(
    override val id: String = UUID.randomUUID().toString(),
    val name: String,
    val category: Category,
    val isFavourite: Boolean
): ListElement

fun generateMockPlace(category: Category? = null): Place {
    val randomNames = listOf("Cafe Mocha", "Burger Spot", "Tech Hub", "Zen Garden", "Book Haven")

    return Place(
        id = UUID.randomUUID().toString(),
        name = randomNames.random(),
        category = category ?: generateMockCategory(),
        isFavourite = Random.nextBoolean()
    )
}