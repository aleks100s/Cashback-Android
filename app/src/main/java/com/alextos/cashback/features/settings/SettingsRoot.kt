package com.alextos.cashback.features.settings

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.alextos.cashback.common.horizontalComposableTransition
import com.alextos.cashback.common.verticalComposableTransition
import com.alextos.cashback.features.category.CategoryRoot
import com.alextos.cashback.features.settings.scenes.settings.presentation.SettingsScreen
import com.alextos.cashback.features.settings.scenes.trashbin.TrashbinScreen
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
                    },
                    openTrashbin = {
                        navController.navigate(SettingsRoute.Trashbin)
                    }
                )
            }

            verticalComposableTransition<SettingsRoute.CategoryCatalog> {
                CategoryRoot(
                    onSelectCategory = {},
                    goBack = {
                        navController.popBackStack()
                    }
                )
            }

            horizontalComposableTransition<SettingsRoute.Trashbin> {
                TrashbinScreen(viewModel = koinViewModel()) {
                    navController.popBackStack()
                }
            }
        }
    }
}