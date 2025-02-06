package com.alextos.cashback.core.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.alextos.cashback.core.data.entities.CardEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CardDao {
    @Query("SELECT * FROM cards WHERE isArchived = 0 ORDER BY isFavourite DESC, name ASC")
    fun getAll(): Flow<List<CardEntity>>

    @Upsert
    fun insert(card: CardEntity)

    @Query("SELECT * FROM cards WHERE id = :id LIMIT 1")
    fun getCard(id: String): Flow<List<CardEntity>>
}