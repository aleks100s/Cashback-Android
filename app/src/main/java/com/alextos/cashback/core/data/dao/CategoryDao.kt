package com.alextos.cashback.core.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.alextos.cashback.core.data.entities.CategoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {
    @Query("SELECT * FROM categories WHERE isArchived = 0")
    fun getAll(): Flow<List<CategoryEntity>>

    @Insert
    suspend fun inset(categoryEntity: CategoryEntity)

    @Query("SELECT * FROM categories WHERE name = :name")
    suspend fun getCategory(name: String): CategoryEntity
}