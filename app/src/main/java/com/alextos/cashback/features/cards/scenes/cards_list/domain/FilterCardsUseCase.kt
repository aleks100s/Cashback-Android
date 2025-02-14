package com.alextos.cashback.features.cards.scenes.cards_list.domain

import com.alextos.cashback.core.domain.models.Card
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FilterCardsUseCase {
    suspend fun execute(cards: List<Card>, query: String): List<Card> {
        return withContext(Dispatchers.IO) {
            val query = query.lowercase()
            return@withContext cards.filter { card ->
                card.toString().lowercase().contains(query)
                        || card.cashback.joinToString { it.category.synonyms?.lowercase() ?: "" }.contains(query)
            }
        }
    }
}