package com.alextos.cashback.core.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import com.alextos.cashback.core.data.entities.CategoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {
    @Query("SELECT * FROM categories ORDER BY priority DESC, name ASC")
    fun getAll(): Flow<List<CategoryEntity>>

    @Query("SELECT * FROM categories WHERE isArchived = 0 ORDER BY priority DESC, name ASC")
    fun getAllUnarchived(): Flow<List<CategoryEntity>>

    @Query("SELECT * FROM categories WHERE id = :categoryId ORDER BY priority DESC, name ASC LIMIT 1")
    suspend fun getCategory(categoryId: String): List<CategoryEntity>

    @Upsert
    suspend fun upsert(categoryEntity: CategoryEntity)

    @Insert
    suspend fun insertAll(categories: List<CategoryEntity>)

    @Delete
    suspend fun delete(categoryEntity: CategoryEntity)
}