package com.alextos.cashback.core.domain.models

import com.alextos.cashback.common.views.ListElement
import java.time.LocalDate
import java.util.UUID
import kotlin.random.Random

data class Payment(
    override val id: String = UUID.randomUUID().toString(),
    val amount: Int,
    val date: LocalDate,
    val card: Card?
): ListElement

fun generateMockPayment(card: Card? = null): Payment {
    return Payment(
        id = UUID.randomUUID().toString(),
        amount = Random.nextInt(),
        date = LocalDate.now(),
        card = card ?: generateMockCard()
    )
}