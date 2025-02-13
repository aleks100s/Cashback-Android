package com.alextos.cashback.features.settings.scenes.settings.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.alextos.cashback.app.CashbackApplication
import com.alextos.cashback.core.domain.settings.SettingsManager
import com.alextos.cashback.core.domain.services.PasteboardService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SettingsViewModel(
    application: Application,
    private val settingsManager: SettingsManager,
    private val pasteboardService: PasteboardService
): AndroidViewModel(application) {
    private val _state = MutableStateFlow(SettingsState())
    val state = _state.asStateFlow()

    init {
        val application = getApplication<CashbackApplication>()
        val packageInfo = application.packageManager.getPackageInfo(application.packageName, 0)
        val versionName = packageInfo.versionName
        val versionCode = packageInfo.longVersionCode

        _state.update {
            it.copy(
                appVersion = versionName ?: "???",
                buildVersion = versionCode
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
            is SettingsAction.ShowCatalog -> {}
            is SettingsAction.ShowCardTrashbin -> {}
            is SettingsAction.ShowCategoryTrashbin -> {}
        }
    }
}