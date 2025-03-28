package com.alextos.cashback.features.settings.scenes.settings.presentation

data class SettingsState(
    val appVersion: String = "",
    val buildVersion: Long = 0,
    val isNotificationsEnabled: Boolean = false,
    val isDisableAdDialogShown: Boolean = false,
    val promoCode: String = "",
    val isImportAlertShown: Boolean = false,
    val isCardsTabEnabled: Boolean = true,
    val isCategoriesTabEnabled: Boolean = true,
    val isPlacesTabEnabled: Boolean = true,
    val isPaymentsTabEnabled: Boolean = true
)
