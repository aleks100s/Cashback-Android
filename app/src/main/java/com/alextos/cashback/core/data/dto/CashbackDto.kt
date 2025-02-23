package com.alextos.cashback.core.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class CashbackDto(
    val id: String,
    val category: CategoryDto,
    val percent: Double,
    val order: Int = 0
)