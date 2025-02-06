package com.alextos.cashback.core.domain.models

import java.time.LocalDate
import java.util.UUID
import kotlin.random.Random

data class Payment(
    val id: UUID,
    val amount: Int,
    val date: LocalDate,
    val card: Card?
)

fun generateMockPayment(card: Card? = null): Payment {
    return Payment(
        id = UUID.randomUUID(),
        amount = Random.nextInt(),
        date = LocalDate.now(),
        card = card ?: generateMockCard()
    )
}