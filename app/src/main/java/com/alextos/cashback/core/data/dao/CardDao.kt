package com.alextos.cashback.core.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.alextos.cashback.core.data.entities.CardEntity
import com.alextos.cashback.core.data.entities.CashbackEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CardDao {
    @Query("SELECT * FROM cards WHERE isArchived = 0 ORDER BY isFavourite DESC, name ASC")
    fun getAllUnarchived(): Flow<List<CardEntity>>

    @Query("SELECT * FROM cards WHERE isArchived = 1 ORDER BY isFavourite DESC, name ASC")
    fun getAllArchived(): Flow<List<CardEntity>>

    @Query("SELECT * FROM cards WHERE isFavourite = 1")
    suspend fun getFavouriteCards(): List<CardEntity>

    @Query("SELECT * FROM cards")
    suspend fun getCardsExport(): List<CardEntity>

    @Upsert
    suspend fun upsert(card: CardEntity)

    @Query("SELECT * FROM cards WHERE id = :id LIMIT 1")
    fun getCardFlow(id: String): Flow<List<CardEntity>>

    @Query("SELECT * FROM cards WHERE id = :id LIMIT 1")
    suspend fun getCard(id: String): List<CardEntity>

    @Query("DELETE FROM cards")
    suspend fun deleteAll()

    @Insert
    suspend fun insertAllCards(cards: List<CardEntity>)

    @Insert
    suspend fun insertAllCashback(cashback: List<CashbackEntity>)

    @Transaction
    suspend fun replaceAllCardsAndCashback(cards: List<CardEntity>, cashback: List<CashbackEntity>) {
        deleteAll()
        insertAllCards(cards)
        insertAllCashback(cashback)
    }
}