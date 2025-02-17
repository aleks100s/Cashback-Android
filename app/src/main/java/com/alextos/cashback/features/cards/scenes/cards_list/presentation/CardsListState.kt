package com.alextos.cashback.features.cards.scenes.cards_list.presentation

import com.alextos.cashback.core.domain.models.Card
import com.alextos.cashback.core.domain.models.Category

data class CardsListState(
    val searchQuery: String = "",
    val allCards: List<Card> = listOf(),
    val filteredCards: List<Card> = listOf(),
    val isAddCardSheetShown: Boolean = false,
    val newCardName: String = "",
    val newCardColor: String = "#E7E7E7",
    val popularCategories: List<Category> = emptyList(),
    val selectedCategory: Category? = null
)