package com.alextos.cashback.core.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class CardDto(
    val id: String,
    var name: String,
    val cashback: List<CashbackDto>,
    var color: String?,
    var isArchived: Boolean,
    var isFavourite: Boolean,
    var currency: String,
    var currencySymbol: String
)
