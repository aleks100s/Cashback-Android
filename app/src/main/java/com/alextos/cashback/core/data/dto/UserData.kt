package com.alextos.cashback.core.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class UserData(
    val categories: List<CategoryDto>,
    val cards: List<CardDto>,
    val places: List<PlaceDto>,
    val payments: List<PaymentDto>
)
