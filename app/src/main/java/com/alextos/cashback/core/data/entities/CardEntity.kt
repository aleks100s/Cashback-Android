package com.alextos.cashback.core.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cards")
data class CardEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    var name: String,
    var color: String?,
    var isArchived: Boolean,
    var isFavourite: Boolean,
    var currency: String,
    var currencySymbol: String
)
