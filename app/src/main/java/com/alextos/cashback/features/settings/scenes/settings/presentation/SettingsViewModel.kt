package com.alextos.cashback.features.settings.scenes.settings.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alextos.cashback.R
import com.alextos.cashback.common.UiText
import com.alextos.cashback.core.AppConstants
import com.alextos.cashback.core.domain.services.AnalyticsEvent
import com.alextos.cashback.core.domain.services.AnalyticsService
import com.alextos.cashback.core.domain.services.AppInfoService
import com.alextos.cashback.core.domain.services.AppInstallationSource
import com.alextos.cashback.core.domain.settings.SettingsManager
import com.alextos.cashback.core.domain.services.PasteboardService
import com.alextos.cashback.core.domain.services.ShareService
import com.alextos.cashback.core.domain.services.ToastService
import com.alextos.cashback.core.domain.services.UserDataDelegate
import com.alextos.cashback.core.domain.services.UserDataService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val settingsManager: SettingsManager,
    private val pasteboardService: PasteboardService,
    private val shareService: ShareService,
    private val appInfoService: AppInfoService,
    private val toastService: ToastService,
    private val userDataService: UserDataService,
    private val analyticsService: AnalyticsService
): ViewModel(), UserDataDelegate {
    private val _state = MutableStateFlow(SettingsState())
    val state = _state.asStateFlow()

    val bannerId = when (appInfoService.installationSource) {
        AppInstallationSource.GOOGLE_PLAY -> "R-M-14460024-2"
        AppInstallationSource.HUAWEI -> "demo-banner-yandex"
        AppInstallationSource.RU_STORE -> "R-M-14164420-2"
    }

    private var counter = 0

    init {
        userDataService.delegate = this
        _state.update {
            it.copy(
                appVersion = appInfoService.versionName,
                buildVersion = appInfoService.versionCode
            )
        }

        viewModelScope.launch(Dispatchers.IO) {
            settingsManager.isNotificationEnabled
                .collect { isEnabled ->
                    _state.update { it.copy(isNotificationsEnabled = isEnabled) }
                }
        }

        viewModelScope.launch(Dispatchers.IO) {
            settingsManager.isAdEnabled
                .collect { isEnabled ->
                    _state.update { it.copy(isAdVisible = isEnabled) }
                }
        }

        viewModelScope.launch(Dispatchers.IO) {
            settingsManager.isCardsTabEnabled
                .collect { isEnabled ->
                    _state.update { it.copy(isCardsTabEnabled = isEnabled) }
                }
        }

        viewModelScope.launch(Dispatchers.IO) {
            settingsManager.isCategoriesTabEnabled
                .collect { isEnabled ->
                    _state.update { it.copy(isCategoriesTabEnabled = isEnabled) }
                }
        }
    }

    fun onAction(action: SettingsAction) {
        when (action) {
            is SettingsAction.CopyValue -> {
                pasteboardService.copy(label = action.label, text = action.text)
                counter += 1
                if (counter == 10) {
                    _state.update { it.copy(isDisableAdDialogShown = true) }
                    counter = 0
                }
            }
            is SettingsAction.SetNotifications -> {
                analyticsService.logEvent(AnalyticsEvent.SettingsToggleNotifications)
                _state.update { it.copy(isNotificationsEnabled = action.enabled) }
                viewModelScope.launch(Dispatchers.IO) {
                    settingsManager.setNotifications(action.enabled)
                }
            }
            is SettingsAction.ShowOnboarding -> {
                analyticsService.logEvent(AnalyticsEvent.SettingsShowOnboardingButtonTapped)
                viewModelScope.launch(Dispatchers.IO) {
                    settingsManager.setOnboarding(shown = false)
                }
            }
            is SettingsAction.ShareApp -> {
                shareService.shareApp(appType = action.appType)
            }
            is SettingsAction.ChangePromoCodeValue -> {
                _state.update { it.copy(promoCode = action.code) }
            }
            is SettingsAction.HidePromoCodePrompt -> {
                _state.update { it.copy(isDisableAdDialogShown = false, promoCode = "") }
            }
            is SettingsAction.ValidatePromoCode -> {
                if (state.value.promoCode == AppConstants.RU_STORE_LINK) {
                    viewModelScope.launch(Dispatchers.IO) {
                        settingsManager.disableAds()
                    }
                    onAction(SettingsAction.HidePromoCodePrompt)
                    toastService.showToast(UiText.StringResourceId(R.string.ad_disabled))
                }
            }
            is SettingsAction.ExportData -> {
                analyticsService.logEvent(AnalyticsEvent.SettingsExportButtonTapped)
                viewModelScope.launch(Dispatchers.IO) {
                    userDataService.exportData()
                    analyticsService.logEvent(AnalyticsEvent.SettingsExportFinished)
                }
            }
            is SettingsAction.ImportData -> {
                _state.update { it.copy(isImportAlertShown = false) }
                userDataService.initiateImport()
            }
            is SettingsAction.ShowImportAlert -> {
                analyticsService.logEvent(AnalyticsEvent.SettingsImportButtonTapped)
                _state.update { it.copy(isImportAlertShown = true) }
            }
            is SettingsAction.HideImportAlert -> {
                _state.update { it.copy(isImportAlertShown = false) }
            }
            is SettingsAction.ShowCardTrashbin -> {
                analyticsService.logEvent(AnalyticsEvent.SettingsOpenCardsTrashbin)
            }
            is SettingsAction.ShowCategoryTrashbin -> {
                analyticsService.logEvent(AnalyticsEvent.SettingsOpenCategoriesTrashbin)
            }
            is SettingsAction.ToggleCardsTab -> {
                analyticsService.logEvent(AnalyticsEvent.SettingsToggleCardsFeature)
                viewModelScope.launch(Dispatchers.IO) {
                    settingsManager.setCardsTab(!state.value.isCardsTabEnabled)
                }
                toastService.showToast(if (!state.value.isCardsTabEnabled) UiText.StringResourceId(R.string.settings_tab_enabled) else UiText.StringResourceId(R.string.settings_tab_disabled))
            }
            is SettingsAction.ToggleCategoriesTab -> {
                analyticsService.logEvent(AnalyticsEvent.SettingsToggleCategoriesFeature)
                viewModelScope.launch(Dispatchers.IO) {
                    settingsManager.setCategoriesTab(!state.value.isCategoriesTabEnabled)
                }
                toastService.showToast(if (!state.value.isCategoriesTabEnabled) UiText.StringResourceId(R.string.settings_tab_enabled) else UiText.StringResourceId(R.string.settings_tab_disabled))
            }
        }
    }

    override fun userDataServiceDidFinishImport() {
        viewModelScope.launch(Dispatchers.Main) {
            analyticsService.logEvent(AnalyticsEvent.SettingsImportFinished)
            toastService.showToast(UiText.StringResourceId(R.string.settings_import_success))
        }
    }
}