package com.alextos.cashback.features.cards

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.alextos.cashback.features.cards.presentation.add_cashback.AddCashbackScreen
import com.alextos.cashback.features.cards.presentation.card_detail.CardDetailScreen
import com.alextos.cashback.features.cards.presentation.cards_list.CardsListScreen
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

            composable<CardsRoute.CardDetail> { navBackStackEntry ->
                val args = navBackStackEntry.toRoute<CardsRoute.CardDetail>()
                val id = args.cardId

                CardDetailScreen(viewModel = koinViewModel()) {
                    navController.navigate(CardsRoute.AddCashback(id))
                }
            }

            composable<CardsRoute.AddCashback> {
                AddCashbackScreen(
                    viewModel = koinViewModel(),
                    selectCategory = {
                        navController.navigate(CardsRoute.SelectCategory)
                    },
                    onSave = {
                        navController.popBackStack()
                    }
                )
            }

            composable<CardsRoute.SelectCategory> {
                CategoryRoot {
                    navController.popBackStack()
                }
            }
        }
    }
}
