package com.alextos.cashback.features.cards.domain.use_cases

import com.alextos.cashback.core.domain.models.Card

class FilterUseCase {
    fun execute(cards: List<Card>, query: String): List<Card> {
        val query = query.lowercase()
        return cards.filter { card ->
            card.name.lowercase().contains(query)
                    || card.toString().lowercase().contains(query)
                    || card.cashback.joinToString { it.category.synonyms?.lowercase() ?: "" }.contains(query)
        }
    }
}