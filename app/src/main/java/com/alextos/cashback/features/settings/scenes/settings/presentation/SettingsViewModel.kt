package com.alextos.cashback.features.settings.scenes.settings.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alextos.cashback.R
import com.alextos.cashback.common.UiText
import com.alextos.cashback.core.AppConstants
import com.alextos.cashback.core.domain.services.AppInfoService
import com.alextos.cashback.core.domain.settings.SettingsManager
import com.alextos.cashback.core.domain.services.PasteboardService
import com.alextos.cashback.core.domain.services.ShareService
import com.alextos.cashback.core.domain.services.ToastService
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
    private val toastService: ToastService
): ViewModel() {
    private val _state = MutableStateFlow(SettingsState())
    val state = _state.asStateFlow()

    private var counter = 0

    init {
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
                _state.update { it.copy(isNotificationsEnabled = action.enabled) }
                viewModelScope.launch(Dispatchers.IO) {
                    settingsManager.setNotifications(action.enabled)
                }
            }
            is SettingsAction.ShowOnboarding -> {
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
            is SettingsAction.ShowCatalog -> {}
            is SettingsAction.ShowCardTrashbin -> {}
            is SettingsAction.ShowCategoryTrashbin -> {}
        }
    }
}