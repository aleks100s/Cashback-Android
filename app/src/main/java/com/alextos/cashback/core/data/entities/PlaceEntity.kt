package com.alextos.cashback.core.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "places")
data class PlaceEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val name: String,
    val categoryId: String,
    val isFavourite: Boolean
)