package com.alextos.cashback.features.settings.di

import com.alextos.cashback.features.settings.scenes.settings.presentation.SettingsViewModel
import com.alextos.cashback.features.settings.scenes.card_trashbin.data.CardTrashbinRepositoryImpl
import com.alextos.cashback.features.settings.scenes.card_trashbin.domain.CardTrashbinRepository
import com.alextos.cashback.features.settings.scenes.card_trashbin.presentation.CardTrashbinViewModel
import com.alextos.cashback.features.settings.scenes.category_trashbin.data.CategoryTrashbinRepositoryImpl
import com.alextos.cashback.features.settings.scenes.category_trashbin.domain.CategoryTrashbinRepository
import com.alextos.cashback.features.settings.scenes.category_trashbin.presentation.CategoryTrashbinViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val settingsModule = module {
    viewModelOf(::SettingsViewModel)
    viewModelOf(::CardTrashbinViewModel)
    viewModelOf(::CategoryTrashbinViewModel)
    factory<CardTrashbinRepository> { CardTrashbinRepositoryImpl(get()) }
    factory<CategoryTrashbinRepository> { CategoryTrashbinRepositoryImpl(get()) }
}