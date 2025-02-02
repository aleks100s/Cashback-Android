package com.alextos.cashback.features.cards

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.alextos.cashback.features.cards.cards_list.presentation.CardsListScreen
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
                    viewModel = koinViewModel()
                ) { card ->
                    navController.navigate(CardsRoute.CardDetail(card.id.toString()))
                }
            }

            composable<CardsRoute.CardDetail> { navBackStackEntry ->
                val args = navBackStackEntry.toRoute<CardsRoute.CardDetail>()
                val id = args.cardId

                Text(text = id)
            }
        }
    }
}
