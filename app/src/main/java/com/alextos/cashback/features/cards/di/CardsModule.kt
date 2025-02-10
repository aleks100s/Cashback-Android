package com.alextos.cashback.features.cards.di

import com.alextos.cashback.features.cards.data.CardsRepositoryImpl
import com.alextos.cashback.features.cards.domain.CardsRepository
import com.alextos.cashback.features.cards.domain.use_cases.FilterCardsUseCase
import com.alextos.cashback.features.cards.domain.use_cases.ValidateCashbackUseCase
import com.alextos.cashback.features.cards.presentation.cashback_detail.CashbackDetailViewModel
import com.alextos.cashback.features.cards.presentation.card_detail.CardDetailViewModel
import com.alextos.cashback.features.cards.presentation.cards_list.CardsListViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val cardsModule = module {
    viewModelOf(::CardsListViewModel)
    viewModelOf(::CardDetailViewModel)
    viewModelOf(::CashbackDetailViewModel)
    factory<CardsRepository> { CardsRepositoryImpl(get(), get()) }
    factory { FilterCardsUseCase() }
    factory { ValidateCashbackUseCase() }
}