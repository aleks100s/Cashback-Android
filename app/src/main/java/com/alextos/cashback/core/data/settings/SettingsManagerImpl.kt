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
    private val context: Context
): SettingsManager {
    companion object {
        private val NOTIFICATIONS_KEY = booleanPreferencesKey("notifications_enabled")
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
}

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")