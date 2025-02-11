package com.alextos.cashback.features.cards

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.alextos.cashback.features.cards.scenes.cashback_detail.presentation.CashbackDetailScreen
import com.alextos.cashback.features.cards.scenes.card_detail.presentation.CardDetailScreen
import com.alextos.cashback.features.cards.scenes.cards_list.presentation.CardsListScreen
import com.alextos.cashback.features.category.CategoryRoot
import org.koin.androidx.compose.koinViewModel

@Composable
fun CardsRoot(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = CardsRoute.CardsGraph
    ) {
        navigation<CardsRoute.CardsGraph>(
            startDestination = CardsRoute.CardsList
        ) {

            composable<CardsRoute.CardsList> {
                CardsListScreen(
                    viewModel = koinViewModel(),
                    onCardSelect = { card ->
                        navController.navigate(CardsRoute.CardDetail(card.id))
                    }
                )
            }

            composable<CardsRoute.CardDetail>(
                enterTransition = { slideInHorizontally(animationSpec = tween(500)) { it } },
                exitTransition = { fadeOut(animationSpec = tween(500)) },
                popEnterTransition = { fadeIn(animationSpec = tween(500)) },
                popExitTransition = { slideOutHorizontally(animationSpec = tween(500)) { it } }
            ) { navBackStackEntry ->
                val args = navBackStackEntry.toRoute<CardsRoute.CardDetail>()
                val id = args.cardId

                CardDetailScreen(
                    viewModel = koinViewModel(),
                    goBack = {
                        navController.popBackStack()
                    },
                    onAddCashback = {
                        navController.navigate(CardsRoute.CashbackDetail(id, null))
                    },
                    onEditCashback = {
                        navController.navigate(CardsRoute.CashbackDetail(id, it))
                    },
                    onDelete = {
                        navController.popBackStack()
                    }
                )
            }

            composable<CardsRoute.CashbackDetail>(
                enterTransition = { slideInHorizontally(animationSpec = tween(500)) { it } },
                exitTransition = { fadeOut(animationSpec = tween(500)) },
                popEnterTransition = { fadeIn(animationSpec = tween(500)) },
                popExitTransition = { slideOutHorizontally(animationSpec = tween(500)) { it } }
            ) {
                CashbackDetailScreen(
                    viewModel = koinViewModel(),
                    goBack = {
                        navController.popBackStack()
                    },
                    selectCategory = {
                        navController.navigate(CardsRoute.SelectCategory)
                    },
                    onSave = {
                        navController.popBackStack()
                    }
                )
            }

            composable<CardsRoute.SelectCategory>(
                enterTransition = { slideInHorizontally(animationSpec = tween(500)) { it } },
                exitTransition = { fadeOut(animationSpec = tween(500)) },
                popEnterTransition = { fadeIn(animationSpec = tween(500)) },
                popExitTransition = { slideOutHorizontally(animationSpec = tween(500)) { it } }
            ) {
                CategoryRoot {
                    navController.popBackStack()
                }
            }
        }
    }
}
