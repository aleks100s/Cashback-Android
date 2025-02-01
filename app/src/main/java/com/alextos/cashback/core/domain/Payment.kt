package com.alextos.cashback.core.domain

import java.time.LocalDate
import java.util.UUID

data class Payment(
    val id: UUID,
    val amount: Int,
    val date: LocalDate,
    val source: Card?
)
