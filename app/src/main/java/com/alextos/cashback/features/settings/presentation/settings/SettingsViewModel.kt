package com.alextos.cashback.features.settings.presentation.settings

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.alextos.cashback.app.CashbackApplication
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SettingsViewModel(application: Application): AndroidViewModel(application) {
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
    }
}