package com.alextos.cashback.features.settings

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.alextos.cashback.features.category.CategoryRoot
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
                SettingsScreen(
                    viewModel = koinViewModel(),
                    openCatalog = {
                        navController.navigate(SettingsRoute.CategoryCatalog)
                    }
                )
            }

            composable<SettingsRoute.CategoryCatalog>(
                enterTransition = { slideInVertically(animationSpec = tween(500)) { it } },
                exitTransition = { fadeOut(animationSpec = tween(500)) },
                popEnterTransition = { fadeIn(animationSpec = tween(500)) },
                popExitTransition = { slideOutVertically(animationSpec = tween(500)) { it } }
            ) {
                CategoryRoot(
                    onSelectCategory = {},
                    goBack = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}