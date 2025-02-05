package com.alextos.cashback.core.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import com.alextos.cashback.core.data.entities.CategoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {
    @Query("SELECT * FROM categories WHERE isArchived = 0")
    fun getAll(): Flow<List<CategoryEntity>>

    @Upsert
    suspend fun insert(categoryEntity: CategoryEntity)

    @Insert
    suspend fun insertAll(categories: List<CategoryEntity>)
}