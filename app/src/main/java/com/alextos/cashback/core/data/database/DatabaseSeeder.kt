package com.alextos.cashback.core.data.database

import android.content.Context
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.alextos.cashback.BuildConfig
import com.alextos.cashback.core.data.mappers.toEntity
import com.alextos.cashback.core.domain.models.Card
import com.alextos.cashback.core.domain.models.Cashback
import com.alextos.cashback.core.domain.models.Category
import com.alextos.cashback.core.domain.models.predefined.PredefinedCategory
import com.alextos.cashback.core.domain.models.predefined.emoji
import com.alextos.cashback.core.domain.models.predefined.info
import com.alextos.cashback.core.domain.models.predefined.localization
import com.alextos.cashback.core.domain.models.predefined.synonyms
import java.util.concurrent.Executors
import kotlin.random.Random

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
                )
            }

        val categoryEntities = categories.map { it.toEntity() }
        val command = categoryEntities.joinToString(separator = ", ") { entity ->
            val synonyms = entity.synonyms?.let { "'$it'" }
            val info = entity.info?.let { "'$it'" }
            val isArchived = if (entity.isArchived) 1 else 0
            "('${entity.id}', '${entity.name}', '${entity.emoji}', $synonyms, ${entity.priority}, $isArchived, $info, ${entity.isNative})"
        }
        db.execSQL("INSERT INTO categories (id, name, emoji, synonyms, priority, isArchived, info, isNative) VALUES $command;")

        if (BuildConfig.DEBUG) {
            val cards = listOf(
                Card(name = "Сбер", color = "#00FF00", isFavourite = true),
                Card(name = "Альфа", color = "#FF0000"),
                Card(name = "ВТБ", color = "#0000FF"),
                Card(name = "Т-Банк", color = "#FFFF00"),
                Card(name = "Газпром", color = "#FF0088"),
                Card(name = "Яндекс", color = "#FF0000"),
                Card(name = "Озон", color = "#0000FF"),
            ).map { it.toEntity() }
            val cardCommand = cards.joinToString(separator = ", ") { entity ->
                "('${entity.id}', '${entity.name}', '${entity.color}', ${entity.isArchived}, ${entity.isFavourite}, '${entity.currency}', '${entity.currencySymbol}')"
            }
            db.execSQL("INSERT INTO cards (id, name, color, isArchived, isFavourite, currency, currencySymbol) VALUES $cardCommand;")

            cards.forEach { card ->
                val cashback = listOf(
                    Cashback(category = categories.random(), percent = Random.nextDouble(0.01, 0.1), order = 0),
                    Cashback(category = categories.random(), percent = Random.nextDouble(0.01, 0.1), order = 1),
                    Cashback(category = categories.random(), percent = Random.nextDouble(0.01, 0.1), order = 2),
                    Cashback(category = categories.random(), percent = Random.nextDouble(0.01, 0.1), order = 3),
                ).map { it.toEntity(card.id) }
                val cashbackCommand = cashback.joinToString(separator = ", ") { entity ->
                    "('${entity.id}', '${entity.categoryId}', ${entity.percent}, '${entity.cardId}', ${entity.order})"
                }
                db.execSQL("INSERT INTO cashback (id, categoryId, percent, cardId, `order`) VALUES $cashbackCommand;")
            }
        }
    }
}