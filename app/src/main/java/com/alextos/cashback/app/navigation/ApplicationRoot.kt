package com.alextos.cashback.app.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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

    Scaffold(
        modifier = Modifier.fillMaxSize(),
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