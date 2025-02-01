package com.alextos.cashback.core.domain

import java.util.UUID

data class Place(
    val id: UUID,
    val name: String,
    val category: Category,
    val isFavourite: Boolean
)
