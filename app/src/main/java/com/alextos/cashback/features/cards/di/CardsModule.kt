package com.alextos.cashback.features.cards.di

import com.alextos.cashback.features.cards.scenes.card_detail.domain.DeleteAllCashbackUseCase
import com.alextos.cashback.features.cards.scenes.card_detail.domain.DeleteCardUseCase
import com.alextos.cashback.features.cards.scenes.card_detail.domain.DeleteCashbackUseCase
import com.alextos.cashback.features.cards.scenes.cards_list.domain.FilterCardsUseCase
import com.alextos.cashback.features.cards.scenes.cashback_detail.domain.ValidateCashbackUseCase
import com.alextos.cashback.features.cards.scenes.cashback_detail.presentation.CashbackDetailViewModel
import com.alextos.cashback.features.cards.scenes.card_detail.presentation.CardDetailViewModel
import com.alextos.cashback.features.cards.scenes.cards_list.presentation.CardsListViewModel
import com.alextos.cashback.features.cards.scenes.cashback_detail.domain.CreateCashbackUseCase
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val cardsModule = module {
    viewModelOf(::CardsListViewModel)
    viewModelOf(::CardDetailViewModel)
    viewModelOf(::CashbackDetailViewModel)
    factory { FilterCardsUseCase() }
    factory { ValidateCashbackUseCase() }
    factory { DeleteAllCashbackUseCase(get()) }
    factory { DeleteCardUseCase(get()) }
    factory { CreateCashbackUseCase(get()) }
    factory { DeleteCashbackUseCase(get()) }
}