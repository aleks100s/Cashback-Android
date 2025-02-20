package com.alextos.cashback.core.domain.settings

import kotlinx.coroutines.flow.Flow

interface SettingsManager {
    val isNotificationEnabled: Flow<Boolean>
    val wasOnboardingShown: Flow<Boolean>
    val isAdEnabled: Flow<Boolean>

    suspend fun setNotifications(enabled: Boolean)
    suspend fun setOnboarding(shown: Boolean)
    suspend fun disableAds()
}