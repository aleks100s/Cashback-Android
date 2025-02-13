package com.alextos.cashback.features.settings.scenes.settings.presentation

sealed interface SettingsAction {
    data class CopyValue(val label: String, val text: String): SettingsAction
    data class SetNotifications(val enabled: Boolean): SettingsAction
    data object ShowCatalog: SettingsAction
    data object ShowCardTrashbin: SettingsAction
    data object ShowCategoryTrashbin: SettingsAction
}