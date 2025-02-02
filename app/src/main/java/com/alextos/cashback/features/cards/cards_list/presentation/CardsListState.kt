package com.alextos.cashback.features.cards.cards_list.presentation

import com.alextos.cashback.core.domain.Card
import com.alextos.cashback.core.domain.generateMockCard

data class CardsListState(
    val searchQuery: String = "",
    val allCards: List<Card> = listOf(
        generateMockCard(isEmpty = true),
        generateMockCard(),
        generateMockCard(),
        generateMockCard(),
        generateMockCard(),
        generateMockCard(),
        generateMockCard(),
        generateMockCard(),
        generateMockCard(),
        generateMockCard(),
        generateMockCard(),
        generateMockCard(),
        generateMockCard(),
        generateMockCard(),
        generateMockCard()
    ),
    val filteredCards: List<Card> = listOf()
)