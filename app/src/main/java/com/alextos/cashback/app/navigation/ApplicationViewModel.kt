package com.alextos.cashback.app.navigation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.alextos.cashback.app.navigation.tabbar.TabBarItem
import com.alextos.cashback.app.notifications.MonthlyNotificationScheduler
import com.alextos.cashback.core.domain.services.AnalyticsEvent
import com.alextos.cashback.core.domain.services.AnalyticsService
import com.alextos.cashback.core.domain.settings.SettingsManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ApplicationViewModel(
    application: Application,
    private val settingsManager: SettingsManager,
    private val analyticsService: AnalyticsService
): AndroidViewModel(application) {
    private val _isOnboardingShown = MutableStateFlow(false)
    val isOnboardingShown = _isOnboardingShown.asStateFlow()

    private val _tabs = MutableStateFlow(listOf(TabBarItem.Cards, TabBarItem.Places, TabBarItem.Categories, TabBarItem.Settings))
    val tabs = _tabs.asStateFlow()

    private val _currentTab = MutableStateFlow<TabBarItem>(TabBarItem.Cards)
    val currentTab = _currentTab.asStateFlow()

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

        viewModelScope.launch(Dispatchers.IO) {
            combine(
                settingsManager.isCardsTabEnabled,
                settingsManager.isCategoriesTabEnabled,
                settingsManager.isPlacesTabEnabled
            ) { isCardsTabEnabled, isCategoriesTabEnabled, isPlacesTabEnabled ->
                listOfNotNull(
                    if (isCardsTabEnabled) TabBarItem.Cards else null,
                    if (isCategoriesTabEnabled) TabBarItem.Categories else null,
                    if (isPlacesTabEnabled) TabBarItem.Places else null,
                    TabBarItem.Settings
                )
            }.collect { list ->
                _tabs.update { list }
            }
        }
    }

    fun hideOnboarding() {
        viewModelScope.launch(Dispatchers.IO) {
            settingsManager.setOnboarding(shown = true)
        }
    }

    fun onTabChange(tab: TabBarItem) {
        _currentTab.update { tab }
        when (tab) {
            TabBarItem.Cards -> {
                analyticsService.logEvent(AnalyticsEvent.CardListAppear)
            }
            TabBarItem.Categories -> {
                analyticsService.logEvent(AnalyticsEvent.SelectCategoryAppear)
            }
            TabBarItem.Settings -> {
                analyticsService.logEvent(AnalyticsEvent.SettingsAppear)
            }
            TabBarItem.Places -> {
                analyticsService.logEvent(AnalyticsEvent.PlacesAppear)
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