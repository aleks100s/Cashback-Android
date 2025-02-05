package com.alextos.cashback.core.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "payments",
    foreignKeys = [
        ForeignKey(
            entity = CardEntity::class,
            parentColumns = ["id"],
            childColumns = ["cardId"],
            onDelete = ForeignKey.CASCADE
        ),
    ],
    indices = [Index("cardId")]
)
data class PaymentEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val amount: Int,
    val date: Long,
    val cardId: String?
)
