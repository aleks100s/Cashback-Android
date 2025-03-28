package com.alextos.cashback.core.data.database

import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.alextos.cashback.BuildConfig
import com.alextos.cashback.core.data.entities.mappers.toEntity
import com.alextos.cashback.core.domain.models.Card
import com.alextos.cashback.core.domain.models.Cashback
import com.alextos.cashback.core.domain.models.Category
import com.alextos.cashback.core.domain.models.Payment
import com.alextos.cashback.core.domain.models.Place
import com.alextos.cashback.core.domain.models.predefined.PredefinedCategory
import com.alextos.cashback.core.domain.models.predefined.emoji
import com.alextos.cashback.core.domain.models.predefined.info
import com.alextos.cashback.core.domain.models.predefined.localization
import com.alextos.cashback.core.domain.models.predefined.synonyms
import java.time.LocalDate
import java.util.concurrent.Executors
import kotlin.random.Random

class DatabaseSeeder : RoomDatabase.Callback() {
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
            "('${entity.id}', '${entity.name}', '${entity.emoji}', $synonyms, 0, 0, $info, 0)"
        }
        db.execSQL("INSERT INTO categories (id, name, emoji, synonyms, priority, isArchived, info, isNative) VALUES $command;")

        if (BuildConfig.DEBUG) {
            val cards = listOf(
                Card(name = "Карта зеленого банка", color = "#00FF00", isFavourite = true),
                Card(name = "Карта красного банка", color = "#FF0000"),
                Card(name = "Карта синего банка", color = "#0000FF"),
                Card(name = "Карта желтого банка", color = "#FFFF00"),
//                Card(name = "Газпром", color = "#FF0088"),
//                Card(name = "Яндекс", color = "#FF0000"),
//                Card(name = "Озон", color = "#0000FF"),
            )
            val cardEntities = cards.map { it.toEntity() }
            val cardCommand = cardEntities.joinToString(separator = ", ") { entity ->
                "('${entity.id}', '${entity.name}', '${entity.color}', 0, ${if (entity.isFavourite) 1 else 0}, '${entity.currency}', '${entity.currencySymbol}')"
            }
            db.execSQL("INSERT INTO cards (id, name, color, isArchived, isFavourite, currency, currencySymbol) VALUES $cardCommand;")

            cardEntities.forEach { card ->
                val cashback = listOf(
                    Cashback(category = categories.random(), percent = Random.nextInt(1, 5).toDouble() / 100, order = 0),
                    Cashback(category = categories.random(), percent = Random.nextInt(1, 5).toDouble() / 100, order = 1),
                    Cashback(category = categories.random(), percent = Random.nextInt(1, 5).toDouble() / 100, order = 2),
                    Cashback(category = categories.random(), percent = Random.nextInt(1, 5).toDouble() / 100, order = 3),
                ).map { it.toEntity(card.id) }
                val cashbackCommand = cashback.joinToString(separator = ", ") { entity ->
                    "('${entity.id}', '${entity.categoryId}', ${entity.percent}, '${entity.cardId}', ${entity.order})"
                }
                db.execSQL("INSERT INTO cashback (id, categoryId, percent, cardId, `order`) VALUES $cashbackCommand;")
            }

            val places = listOf(
                Place(name = "Магазин через дорогу", category = categories.random(), isFavourite = true),
                Place(name = "Бар", category = categories.random(), isFavourite = false),
                Place(name = "Кафешка в центре", category = categories.random(), isFavourite = false),
                Place(name = "Любимая шаурма", category = categories.random(), isFavourite = false),
            )
            places.forEach { place ->
                val placeCommand = "('${place.id}', '${place.name}', '${place.category.id}', ${if (place.isFavourite) 1 else 0})"
                db.execSQL("INSERT INTO places (id, name, categoryId, isFavourite) VALUES $placeCommand;")
            }

            val payments = listOf(
                Payment(amount = Random.nextInt(100, 1000), date = LocalDate.of(2024, Random.nextInt(1, 12), Random.nextInt(1, 28)), card = cards.random()),
                Payment(amount = Random.nextInt(100, 1000), date = LocalDate.now(), card = cards.random()),
                Payment(amount = Random.nextInt(100, 1000), date = LocalDate.of(2024, Random.nextInt(1, 12), Random.nextInt(1, 28)), card = cards.random()),
                Payment(amount = Random.nextInt(100, 1000), date = LocalDate.now(), card = cards.random()),
                Payment(amount = Random.nextInt(100, 1000), date = LocalDate.of(2024, Random.nextInt(1, 12), Random.nextInt(1, 28)), card = cards.random()),
                Payment(amount = Random.nextInt(100, 1000), date = LocalDate.now(), card = cards.random()),
                Payment(amount = Random.nextInt(100, 1000), date = LocalDate.of(2024, Random.nextInt(1, 12), Random.nextInt(1, 28)), card = cards.random()),
                Payment(amount = Random.nextInt(100, 1000), date = LocalDate.now(), card = cards.random()),
                Payment(amount = Random.nextInt(100, 1000), date = LocalDate.of(2025, Random.nextInt(1, 3), Random.nextInt(1, 28)), card = cards.random())
            ).map { it.toEntity() }
            payments.forEach { payment ->
                val paymentCommand = "('${payment.id}', ${payment.amount}, ${payment.date}, '${payment.cardId}')"
                db.execSQL("INSERT INTO payments (id, amount, date, cardId) VALUES $paymentCommand;")
            }
        }
    }
}