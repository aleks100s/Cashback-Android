package com.alextos.cashback.core.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.alextos.cashback.core.data.entities.combined_entities.PlaceWithCategory
import com.alextos.cashback.core.data.entities.PlaceEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PlaceDao {
    @Transaction
    @Query("SELECT * FROM places ORDER BY isFavourite DESC")
    fun getAll(): Flow<List<PlaceWithCategory>>

    @Query("SELECT * FROM places WHERE id = :id")
    fun getPlace(id: String): Flow<PlaceWithCategory?>

    @Upsert
    suspend fun upsert(place: PlaceEntity)

    @Delete
    suspend fun delete(place: PlaceEntity)
}