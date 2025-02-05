package com.alextos.cashback.features.settings

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.alextos.cashback.features.settings.presentation.settings.SettingsScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun SettingsRoot(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = SettingsRoute.SettingsGraph
    ) {
        navigation<SettingsRoute.SettingsGraph>(
            startDestination = SettingsRoute.Settings
        ) {
            composable<SettingsRoute.Settings> {
                SettingsScreen(viewModel = koinViewModel())
            }
        }
    }
}