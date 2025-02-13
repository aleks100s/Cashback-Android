package com.alextos.cashback.app

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.alextos.cashback.app.navigation.ApplicationRoot
import com.alextos.cashback.app.theme.CashbackTheme
import com.alextos.cashback.core.domain.settings.SettingsManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.inject

// Константа для идентификации запроса
private const val NOTIFICATION_PERMISSION_REQUEST_CODE = 1001

class MainActivity : ComponentActivity() {
    private val settingsManager by inject<SettingsManager>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CashbackTheme {
                ApplicationRoot()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
    override fun onResume() {
        super.onResume()
        requestNotificationPermission()
    }

    @RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
    fun requestNotificationPermission() {
        // Проверяем, предоставлено ли разрешение
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
            != PackageManager.PERMISSION_GRANTED) {
            // Запрашиваем разрешение
            requestPermissions(
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                NOTIFICATION_PERMISSION_REQUEST_CODE
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
        deviceId: Int
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults, deviceId)
        if (requestCode == NOTIFICATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Разрешение получено, можно показывать уведомления
                lifecycleScope.launch(Dispatchers.IO) {
                    withContext(Dispatchers.IO) {
                        settingsManager.setNotifications(true)
                    }
                }
            } else {
                // Разрешение отклонено – уведомления показывать не получится
            }
        }
    }
}