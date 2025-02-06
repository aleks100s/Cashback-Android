package com.alextos.cashback.features.settings.presentation.settings

sealed interface SettingsAction {
    data class CopyValue(val label: String, val text: String): SettingsAction
}