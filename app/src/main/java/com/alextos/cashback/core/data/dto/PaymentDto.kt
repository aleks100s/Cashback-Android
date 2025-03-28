package com.alextos.cashback.core.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class PaymentDto(
    val id: String,
    val amount: Int,
    val date: Double,
    val source: CardDto? = null
)
