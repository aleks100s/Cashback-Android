package com.alextos.cashback.app.navigation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.alextos.cashback.app.navigation.tabbar.TabBarItem
import com.alextos.cashback.app.navigation.tabbar.TabBarView
import com.alextos.cashback.app.onboarding.OnboardingScreen
import com.alextos.cashback.features.cards.CardsRoot
import com.alextos.cashback.features.category.CategoryRoot
import com.alextos.cashback.features.places.PlacesRoot
import com.alextos.cashback.features.settings.SettingsRoot
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ApplicationRoot(viewModel: ApplicationViewModel = koinViewModel()) {
    val navController = rememberNavController()
    val focusManager = LocalFocusManager.current
    val isOnboardingShown by viewModel.isOnboardingShown.collectAsStateWithLifecycle()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val tabs by viewModel.tabs.collectAsStateWithLifecycle()
    val currentTab by viewModel.currentTab.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier.fillMaxSize()
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                focusManager.clearFocus()
            },
        bottomBar = {
            TabBarView(navController, tabs = tabs, onTabChange = viewModel::onTabChange)
        }
    ) { innerPadding ->
        NavHost(
            modifier = Modifier.padding(bottom = innerPadding.calculateBottomPadding()),
            navController = navController,
            startDestination = currentTab
        ) {
            composable<TabBarItem.Cards> {
                CardsRoot()
            }

            composable<TabBarItem.Settings> {
                SettingsRoot()
            }

            composable<TabBarItem.Categories> {
                CategoryRoot(disableSelection = true, onSelectCategory = {}, disableBackButton = true) { }
            }

            composable<TabBarItem.Places> {
                PlacesRoot()
            }
        }
    }

    if (isOnboardingShown) {
        ModalBottomSheet(
            onDismissRequest = {
                viewModel.hideOnboarding()
            },
            sheetState = sheetState,
            dragHandle = null
        ) {
            OnboardingScreen {
                viewModel.hideOnboarding()
            }
        }
    }
}