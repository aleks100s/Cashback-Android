package com.alextos.cashback.features.settings.di

import com.alextos.cashback.features.settings.scenes.settings.presentation.SettingsViewModel
import com.alextos.cashback.features.settings.scenes.trashbin.TrashbinViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val settingsModule = module {
    viewModelOf(::SettingsViewModel)
    viewModelOf(::TrashbinViewModel)
}