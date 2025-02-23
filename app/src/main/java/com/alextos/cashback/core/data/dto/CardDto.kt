package com.alextos.cashback.core.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class CardDto(
    val id: String,
    var name: String,
    val cashback: List<CashbackDto>,
    var color: String? = "#E7E7E7",
    var isArchived: Boolean = false,
    var isFavorite: Boolean = false,
    var currency: String = "",
    var currencySymbol: String = ""
)
