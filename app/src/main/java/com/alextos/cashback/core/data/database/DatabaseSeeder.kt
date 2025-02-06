package com.alextos.cashback.core.data.database

import android.content.Context
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.alextos.cashback.core.data.mappers.toEntity
import com.alextos.cashback.core.domain.models.Category
import com.alextos.cashback.core.domain.models.predefined.PredefinedCategory
import com.alextos.cashback.core.domain.models.predefined.emoji
import com.alextos.cashback.core.domain.models.predefined.localization
import com.alextos.cashback.core.domain.models.predefined.synonyms
import java.util.UUID
import java.util.concurrent.Executors

class DatabaseSeeder(private val context: Context) : RoomDatabase.Callback() {
    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)

        Executors.newSingleThreadExecutor().execute {
            seedDatabase(db)
        }
    }

    private fun seedDatabase(db: SupportSQLiteDatabase) {
        val categories = PredefinedCategory.entries
            .map { predefinedCategory ->
                Category(
                    id = UUID.randomUUID(),
                    name = predefinedCategory.localization,
                    emoji = predefinedCategory.emoji,
                    synonyms = predefinedCategory.synonyms.joinToString(","),
                    priority = 0,
                    isArchived = false
                ).toEntity()
            }
            val command = categories
                .map { entity ->
                    val synonyms = entity.synonyms?.let { "'${it}'" }
                    val isArchived = if (entity.isArchived) 1 else 0
                    "('${entity.id}', '${entity.name}', '${entity.emoji}', $synonyms, ${entity.priority}, $isArchived)"
                }
                .joinToString(separator = ", ")
        db.execSQL("INSERT INTO categories (id, name, emoji, synonyms, priority, isArchived) VALUES $command;")
    }
}