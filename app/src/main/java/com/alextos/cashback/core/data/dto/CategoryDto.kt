package com.alextos.cashback.core.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class CategoryDto(
    val id: String,
    val name: String,
    val emoji: String,
    val synonyms: String? = null,
    var priority: Int = 0,
    var isArchived: Boolean = false,
    val info: String? = null,
    val isNative: Boolean = true
)