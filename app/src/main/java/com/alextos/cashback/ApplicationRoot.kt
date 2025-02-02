package com.alextos.cashback

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.alextos.cashback.features.cards.CardsRoot

@Composable
fun ApplicationRoot() {
    val tabs = listOf(TabBarItem.Cards, TabBarItem.Settings)
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar {
                tabs.forEach { tab ->
                    val isActive = when(tab) {
                        is TabBarItem.Cards -> currentRoute == "com.alextos.cashback.TabBarItem.Cards"
                        is TabBarItem.Settings -> currentRoute == "com.alextos.cashback.TabBarItem.Settings"
                    }
                    NavigationBarItem(
                        label = {
                            Text(text = tab.title)
                        },
                        alwaysShowLabel = true,
                        icon = {
                            Icon(
                                imageVector = when(tab) {
                                    is TabBarItem.Cards -> Icons.Default.Email
                                    is TabBarItem.Settings -> Icons.Default.Settings
                                },
                                contentDescription = tab.title
                            )
                        },
                        selected = isActive,
                        colors = NavigationBarItemColors(
                            selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                            selectedTextColor = MaterialTheme.colorScheme.primary,
                            unselectedIconColor = MaterialTheme.colorScheme.secondary,
                            unselectedTextColor = MaterialTheme.colorScheme.secondary,
                            selectedIndicatorColor = MaterialTheme.colorScheme.primary,
                            disabledIconColor = MaterialTheme.colorScheme.onTertiary,
                            disabledTextColor = MaterialTheme.colorScheme.tertiary
                        ),
                        onClick = {
                            navController.navigate(tab) {
                                // Очищает стек табов
                                navController.graph.startDestinationRoute?.let { route ->
                                    popUpTo(route) {
                                        saveState = true
                                    }
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                    )
                }
            }
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
                Text(text = "Настройки")
            }
        }
    }
}