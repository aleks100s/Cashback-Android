package com.alextos.cashback.features.settings.scenes.settings.presentation

data class SettingsState(
    val appVersion: String = "",
    val buildVersion: Long = 0,
    val isNotificationsEnabled: Boolean = false,
    val isDisableAdDialogShown: Boolean = false,
    val promoCode: String = "",
    val isAdVisible: Boolean = false
)
