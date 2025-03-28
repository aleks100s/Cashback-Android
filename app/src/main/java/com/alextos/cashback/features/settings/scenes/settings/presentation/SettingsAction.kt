package com.alextos.cashback.features.settings.scenes.settings.presentation

import com.alextos.cashback.core.domain.services.AppType

sealed interface SettingsAction {
    data class CopyValue(val label: String, val text: String): SettingsAction
    data class SetNotifications(val enabled: Boolean): SettingsAction
    data object ShowCardTrashbin: SettingsAction
    data object ShowCategoryTrashbin: SettingsAction
    data object ShowOnboarding: SettingsAction
    data class ShareApp(val appType: AppType): SettingsAction
    data class ChangePromoCodeValue(val code: String): SettingsAction
    data object HidePromoCodePrompt: SettingsAction
    data object ValidatePromoCode: SettingsAction
    data object ExportData: SettingsAction
    data object ShowImportAlert: SettingsAction
    data object HideImportAlert: SettingsAction
    data object ImportData: SettingsAction
    data object ToggleCardsTab: SettingsAction
    data object ToggleCategoriesTab: SettingsAction
    data object TogglePlacesTab: SettingsAction
    data object TogglePaymentsTab: SettingsAction
}