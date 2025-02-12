package com.alextos.cashback.features.settings.scenes.settings.presentation

sealed interface SettingsAction {
    data class CopyValue(val label: String, val text: String): SettingsAction
    data object ShowCatalog: SettingsAction
    data object ShowTrashbin: SettingsAction
}