package com.alextos.cashback.core.domain.settings

import kotlinx.coroutines.flow.Flow

interface SettingsManager {
    val isNotificationEnabled: Flow<Boolean>
    val wasOnboardingShown: Flow<Boolean>
    val isAdEnabled: Flow<Boolean>
    val isCardsTabEnabled: Flow<Boolean>
    val isCategoriesTabEnabled: Flow<Boolean>
    val isPlacesTabEnabled: Flow<Boolean>
    val isCompactCardViewEnabled: Flow<Boolean>

    suspend fun setNotifications(enabled: Boolean)
    suspend fun setOnboarding(shown: Boolean)
    suspend fun disableAds()
    suspend fun setCardsTab(enabled: Boolean)
    suspend fun setCategoriesTab(enabled: Boolean)
    suspend fun setPlacesTab(enabled: Boolean)
    suspend fun setCompactCardView(enabled: Boolean)
}