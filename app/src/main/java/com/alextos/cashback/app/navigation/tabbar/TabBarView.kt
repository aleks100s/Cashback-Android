package com.alextos.cashback.app.navigation.tabbar

import androidx.compose.material3.NavigationBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun TabBarView(
    navController: NavHostController,
    tabs: List<TabBarItem>,
    onTabChange: (TabBarItem) -> Unit
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    LaunchedEffect(currentRoute) {
        tabs.forEach { tab ->
            val isActive = when(tab) {
                is TabBarItem.Cards -> currentRoute == TabBarItem.Cards.javaClass.canonicalName
                is TabBarItem.Settings -> currentRoute == TabBarItem.Settings.javaClass.canonicalName
                is TabBarItem.Categories -> currentRoute == TabBarItem.Categories.javaClass.canonicalName
                is TabBarItem.Places -> currentRoute == TabBarItem.Places.javaClass.canonicalName
            }
            if (isActive) {
                onTabChange(tab)
            }
        }
    }

    NavigationBar {
        tabs.forEach { tab ->
            val isActive = when(tab) {
                is TabBarItem.Cards -> currentRoute == TabBarItem.Cards.javaClass.canonicalName
                is TabBarItem.Settings -> currentRoute == TabBarItem.Settings.javaClass.canonicalName
                is TabBarItem.Categories -> currentRoute == TabBarItem.Categories.javaClass.canonicalName
                is TabBarItem.Places -> currentRoute == TabBarItem.Places.javaClass.canonicalName
            }

            TabBarItemView(tab, isActive) {
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
            }
        }
    }
}