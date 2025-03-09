package com.alextos.cashback.features.settings

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.alextos.cashback.common.transitions.horizontalComposableTransition
import com.alextos.cashback.features.settings.scenes.settings.presentation.SettingsScreen
import com.alextos.cashback.features.settings.scenes.card_trashbin.presentation.CardTrashbinScreen
import com.alextos.cashback.features.settings.scenes.category_trashbin.presentation.CategoryTrashbinScreen
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
                    openCardTrashbin = {
                        navController.navigate(SettingsRoute.CardTrashbin)
                    },
                    openCategoryTrashbin = {
                        navController.navigate(SettingsRoute.CategoryTrashbin)
                    }
                )
            }

            horizontalComposableTransition<SettingsRoute.CardTrashbin> {
                CardTrashbinScreen(viewModel = koinViewModel()) {
                    navController.popBackStack()
                }
            }

            horizontalComposableTransition<SettingsRoute.CategoryTrashbin> {
                CategoryTrashbinScreen(viewModel = koinViewModel()) {
                    navController.popBackStack()
                }
            }
        }
    }
}