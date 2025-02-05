package com.alextos.cashback.features.cards.di

import com.alextos.cashback.features.cards.data.CardsRepositoryImpl
import com.alextos.cashback.features.cards.domain.CardsRepository
import com.alextos.cashback.features.cards.presentation.cards_list.CardsListViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val cardsModule = module {
    viewModelOf(::CardsListViewModel)
    factory<CardsRepository> { CardsRepositoryImpl(get(), get()) }
}