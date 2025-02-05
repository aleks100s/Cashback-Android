package com.alextos.cashback.core.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.alextos.cashback.core.domain.Category

@Entity(
    tableName = "places",
    foreignKeys = [
        ForeignKey(
            entity = CategoryEntity::class,
            parentColumns = ["id"],
            childColumns = ["categoryId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("categoryId")]
)
data class PlaceEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val name: String,
    val categoryId: String,
    val isFavourite: Boolean
)