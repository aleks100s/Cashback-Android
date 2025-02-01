package com.alextos.cashback.features.cards.di

import com.alextos.cashback.features.cards.cards_list.presentation.CardsListViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val cardsModule = module {
    viewModelOf(::CardsListViewModel)
}