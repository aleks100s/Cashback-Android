package com.alextos.cashback.app.navigation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.alextos.cashback.app.notifications.MonthlyNotificationScheduler
import com.alextos.cashback.core.domain.settings.SettingsManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ApplicationViewModel(
    application: Application,
    private val settingsManager: SettingsManager
): AndroidViewModel(application) {
    private val _isOnboardingShown = MutableStateFlow(false)
    val isOnboardingShown = _isOnboardingShown.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            settingsManager.isNotificationEnabled
                .drop(1)
                .collect { isEnabled ->
                    if (isEnabled) {
                        turnOnNotifications()
                    } else {
                        turnOffNotifications()
                    }
                }
        }

        viewModelScope.launch(Dispatchers.IO) {
            settingsManager.wasOnboardingShown
                .collect { wasShown ->
                    _isOnboardingShown.update { !wasShown }
                }
        }
    }

    fun hideOnboarding() {
        viewModelScope.launch(Dispatchers.IO) {
            settingsManager.setOnboarding(shown = true)
        }
    }

    private fun turnOnNotifications() {
        turnOffNotifications()
        MonthlyNotificationScheduler.scheduleNextNotification(getApplication())
    }

    private fun turnOffNotifications() {
        MonthlyNotificationScheduler.cancelScheduledNotification(getApplication())
    }
}