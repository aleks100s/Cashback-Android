package com.alextos.cashback.app.navigation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.alextos.cashback.app.navigation.tabbar.TabBarItem
import com.alextos.cashback.app.navigation.tabbar.TabBarView
import com.alextos.cashback.features.cards.CardsRoot
import com.alextos.cashback.features.settings.SettingsRoot
import org.koin.androidx.compose.koinViewModel

@Composable
fun ApplicationRoot(viewModel: ApplicationViewModel = koinViewModel()) {
    val navController = rememberNavController()
    val focusManager = LocalFocusManager.current

    Scaffold(
        modifier = Modifier.fillMaxSize()
            .clickable(
                indication = null, // Убираем ripple-эффект
                interactionSource = remember { MutableInteractionSource() }
            ) {
                focusManager.clearFocus()
            },
        bottomBar = {
            TabBarView(navController)
        }
    ) { innerPadding ->
        NavHost(
            modifier = Modifier.padding(bottom = innerPadding.calculateBottomPadding()),
            navController = navController,
            startDestination = TabBarItem.Cards
        ) {
            composable<TabBarItem.Cards> {
                CardsRoot()
            }

            composable<TabBarItem.Settings> {
                SettingsRoot()
            }
        }
    }
}