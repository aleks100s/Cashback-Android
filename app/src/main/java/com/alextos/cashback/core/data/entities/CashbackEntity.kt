package com.alextos.cashback.core.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cashback")
data class CashbackEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val categoryId: String,
    val percent: Double,
    val cardId: String
)
