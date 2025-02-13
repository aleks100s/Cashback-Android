package com.alextos.cashback.app.navigation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.alextos.cashback.core.data.notifications.MonthlyNotificationScheduler
import com.alextos.cashback.core.domain.settings.SettingsManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.launch

class ApplicationViewModel(
    application: Application,
    private val settingsManager: SettingsManager
): AndroidViewModel(application) {
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
    }

    private fun turnOnNotifications() {
        turnOffNotifications()
        MonthlyNotificationScheduler.scheduleNextNotification(getApplication())
    }

    private fun turnOffNotifications() {
        MonthlyNotificationScheduler.cancelScheduledNotification(getApplication())
    }
}