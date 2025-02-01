package com.alextos.cashback.core.domain

import java.util.UUID

data class Category(
    val id: UUID,
    val name: String,
    val emoji: String,
    val synonyms: String?,
    val priority: Int,
    val isArchived: Boolean
)
