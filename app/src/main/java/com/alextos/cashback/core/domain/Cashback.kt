package com.alextos.cashback.core.domain

import java.util.UUID

data class Cashback(
    val id: UUID,
    val category: Category,
    val percent: Double
)
