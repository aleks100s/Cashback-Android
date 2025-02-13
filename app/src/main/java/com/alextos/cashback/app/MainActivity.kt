package com.alextos.cashback.app

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Rect
import android.os.Build
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.alextos.cashback.app.navigation.ApplicationRoot
import com.alextos.cashback.app.notifications.MonthlyNotificationScheduler
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

    override fun onResume() {
        super.onResume()
        requestNotificationPermission()
    }

    private fun requestNotificationPermission() {
        // Проверяем, предоставлено ли разрешение
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
            != PackageManager.PERMISSION_GRANTED) {
            // Запрашиваем разрешение

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    NOTIFICATION_PERMISSION_REQUEST_CODE
                )
            } else {
                allowNotifications()
            }
        } else {
            allowNotifications()
        }
    }

    @Deprecated("This method has been deprecated in favor of using the Activity Result API\n      which brings increased type safety via an {@link ActivityResultContract} and the prebuilt\n      contracts for common intents available in\n      {@link androidx.activity.result.contract.ActivityResultContracts}, provides hooks for\n      testing, and allow receiving results in separate, testable classes independent from your\n      activity. Use\n      {@link #registerForActivityResult(ActivityResultContract, ActivityResultCallback)} passing\n      in a {@link RequestMultiplePermissions} object for the {@link ActivityResultContract} and\n      handling the result in the {@link ActivityResultCallback#onActivityResult(Object) callback}.")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == NOTIFICATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Разрешение получено, можно показывать уведомления
                allowNotifications()
            } else {
                // Разрешение отклонено – уведомления показывать не получится
            }
        }
    }

    private fun allowNotifications() {
        lifecycleScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.IO) {
                settingsManager.setNotifications(true)
            }
        }
        MonthlyNotificationScheduler.createNotificationChannel(this)
        MonthlyNotificationScheduler.scheduleNextNotification(this)
    }
}