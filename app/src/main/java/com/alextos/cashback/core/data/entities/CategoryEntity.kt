package com.alextos.cashback.core.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categories")
data class CategoryEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val name: String,
    val emoji: String,
    val synonyms: String?,
    val priority: Int,
    val isArchived: Boolean,
    val info: String?,
    val isNative: Boolean
)