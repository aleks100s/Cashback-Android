package com.alextos.cashback.core.data.database

import android.content.Context
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.alextos.cashback.core.data.mappers.toEntity
import com.alextos.cashback.core.domain.models.Category
import com.alextos.cashback.core.domain.models.predefined.PredefinedCategory
import com.alextos.cashback.core.domain.models.predefined.emoji
import com.alextos.cashback.core.domain.models.predefined.info
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
                    name = predefinedCategory.localization,
                    emoji = predefinedCategory.emoji,
                    synonyms = predefinedCategory.synonyms.joinToString(","),
                    priority = 0,
                    isArchived = false,
                    info = predefinedCategory.info,
                    isNative = true
                ).toEntity()
            }
            val command = categories.joinToString(separator = ", ") { entity ->
                val synonyms = entity.synonyms?.let { "'$it'" }
                val info = entity.info?.let { "'$it'" }
                val isArchived = if (entity.isArchived) 1 else 0
                "('${entity.id}', '${entity.name}', '${entity.emoji}', $synonyms, ${entity.priority}, $isArchived, $info, ${entity.isNative})"
            }
        db.execSQL("INSERT INTO categories (id, name, emoji, synonyms, priority, isArchived, info, isNative) VALUES $command;")
    }
}