package com.alextos.cashback.core.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.alextos.cashback.core.data.entities.CardEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CardDao {
    @Query("SELECT * FROM cards WHERE isArchived = 0")
    fun getAll(): Flow<List<CardEntity>>

    @Upsert
    fun insert(card: CardEntity)
}