package com.alextos.cashback.core.domain

import java.util.UUID

data class Card(
    val id: UUID,
    var name: String,
    val cashback: List<Cashback>,
    var color: String?,
    var isArchived: Boolean,
    var isFavourite: Boolean,
    var currency: String,
    var currencySymbol: String
)
