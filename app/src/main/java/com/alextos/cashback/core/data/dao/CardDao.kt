package com.alextos.cashback.core.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.alextos.cashback.core.data.entities.CardEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CardDao {
    @Query("SELECT * FROM cards WHERE isArchived = 0 ORDER BY isFavourite DESC, name ASC")
    fun getAllUnarchived(): Flow<List<CardEntity>>

    @Query("SELECT * FROM cards WHERE isArchived = 1 ORDER BY isFavourite DESC, name ASC")
    fun getAllArchived(): Flow<List<CardEntity>>

    @Upsert
    suspend fun upsert(card: CardEntity)

    @Query("SELECT * FROM cards WHERE id = :id LIMIT 1")
    fun getCardFlow(id: String): Flow<List<CardEntity>>

    @Query("SELECT * FROM cards WHERE id = :id LIMIT 1")
    suspend fun getCard(id: String): List<CardEntity>
}