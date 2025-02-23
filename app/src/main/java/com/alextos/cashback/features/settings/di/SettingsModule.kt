package com.alextos.cashback.features.settings.di

import com.alextos.cashback.features.settings.scenes.settings.presentation.SettingsViewModel
import com.alextos.cashback.features.settings.scenes.card_trashbin.presentation.CardTrashbinViewModel
import com.alextos.cashback.features.settings.scenes.category_trashbin.presentation.CategoryTrashbinViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val settingsModule = module {
    viewModelOf(::SettingsViewModel)
    viewModelOf(::CardTrashbinViewModel)
    viewModelOf(::CategoryTrashbinViewModel)
}