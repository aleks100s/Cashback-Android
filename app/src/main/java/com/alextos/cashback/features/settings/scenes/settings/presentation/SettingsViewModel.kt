package com.alextos.cashback.features.settings.scenes.settings.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alextos.cashback.core.domain.services.AppInfoService
import com.alextos.cashback.core.domain.settings.SettingsManager
import com.alextos.cashback.core.domain.services.PasteboardService
import com.alextos.cashback.core.domain.services.ShareService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val settingsManager: SettingsManager,
    private val pasteboardService: PasteboardService,
    private val shareService: ShareService,
    private val appInfoService: AppInfoService
): ViewModel() {
    private val _state = MutableStateFlow(SettingsState())
    val state = _state.asStateFlow()

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
    }

    fun onAction(action: SettingsAction) {
        when (action) {
            is SettingsAction.CopyValue -> {
                pasteboardService.copy(label = action.label, text = action.text)
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
            is SettingsAction.ShowCatalog -> {}
            is SettingsAction.ShowCardTrashbin -> {}
            is SettingsAction.ShowCategoryTrashbin -> {}
        }
    }
}