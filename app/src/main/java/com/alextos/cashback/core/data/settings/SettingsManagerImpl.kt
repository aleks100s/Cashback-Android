package com.alextos.cashback.core.data.settings

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.alextos.cashback.core.domain.settings.SettingsManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingsManagerImpl(
    context: Context
): SettingsManager {
    companion object {
        private val NOTIFICATIONS_KEY = booleanPreferencesKey("notifications_enabled")
        private val ONBOARDING_KEY = booleanPreferencesKey("was_onboarding_shown")
        private val ADS_KEY = booleanPreferencesKey("is_ad_enabled")
    }

    private val dataStore = context.dataStore

    override val isNotificationEnabled: Flow<Boolean>
        get() = dataStore.data.map { preferences ->
            preferences[NOTIFICATIONS_KEY] ?: false
        }

    override suspend fun setNotifications(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[NOTIFICATIONS_KEY] = enabled
        }
    }

    override val wasOnboardingShown: Flow<Boolean>
        get() = dataStore.data.map { preferences ->
            preferences[ONBOARDING_KEY] ?: false
        }

    override suspend fun setOnboarding(shown: Boolean) {
        dataStore.edit { preferences ->
            preferences[ONBOARDING_KEY] = shown
        }
    }

    override val isAdEnabled: Flow<Boolean>
        get() = dataStore.data.map { preferences ->
            preferences[ADS_KEY] ?: true
        }

    override suspend fun disableAds() {
        dataStore.edit { preferences ->
            preferences[ADS_KEY] = false
        }
    }
}

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")