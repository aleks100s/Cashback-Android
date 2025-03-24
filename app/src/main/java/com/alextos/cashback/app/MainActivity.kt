package com.alextos.cashback.app

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.alextos.cashback.app.navigation.ApplicationRoot
import com.alextos.cashback.app.notifications.MonthlyNotificationScheduler
import com.alextos.cashback.app.theme.CashbackTheme
import com.alextos.cashback.core.domain.services.UserDataFileProvider
import com.alextos.cashback.core.domain.services.UserDataService
import com.alextos.cashback.core.domain.settings.SettingsManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.inject

// Константа для идентификации запроса
private const val NOTIFICATION_PERMISSION_REQUEST_CODE = 1001

class MainActivity : ComponentActivity(), UserDataFileProvider {
    private val settingsManager by inject<SettingsManager>()
    private val userDataService by inject<UserDataService>()

    // Запуск выбора файла
    private val pickJsonFile = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.data?.let { uri ->
                readJsonFromUri(uri)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userDataService.provider = this
        enableEdgeToEdge()
        setContent {
            CashbackTheme {
                ApplicationRoot(deepLinkIntent = intent)
            }
        }
    }

    @Override
    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        intent.data?.let { uri ->
            if (uri.scheme == "app" && uri.host == "com.alextos.cashback") {
                setContent {
                    CashbackTheme {
                        ApplicationRoot(deepLinkIntent = intent)
                    }
                }
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

    override fun showFilePicker() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "*/*"
            putExtra(Intent.EXTRA_MIME_TYPES, arrayOf(
                "application/octet-stream", // Для неизвестных типов
                "text/plain",
                "application/json"
            ))
        }
        pickJsonFile.launch(intent)
    }

    private fun readJsonFromUri(uri: Uri) {
        contentResolver.openInputStream(uri)?.use { inputStream ->
            val jsonString = inputStream.bufferedReader().use { it.readText() }
            lifecycleScope.launch(Dispatchers.IO) {
                userDataService.continueImport(jsonString)
            }
        }
    }
}