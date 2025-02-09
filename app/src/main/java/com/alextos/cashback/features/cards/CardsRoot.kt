package com.alextos.cashback.features.cards

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.alextos.cashback.features.cards.presentation.add_cashback.AddCashbackScreen
import com.alextos.cashback.features.cards.presentation.card_detail.CardDetailScreen
import com.alextos.cashback.features.cards.presentation.cards_list.CardsListScreen
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
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
                        navController.navigate(CardsRoute.CardDetail(card.id.toString()))
                    }
                )
            }

            composable<CardsRoute.CardDetail> { navBackStackEntry ->
                val args = navBackStackEntry.toRoute<CardsRoute.CardDetail>()
                val id = args.cardId

                var isAddCashbackSheetShown by remember { mutableStateOf(false) }

                CardDetailScreen(viewModel = koinViewModel()) {
                    isAddCashbackSheetShown = true
                }

                if (isAddCashbackSheetShown) {
                    ModalBottomSheet(onDismissRequest = {
                        isAddCashbackSheetShown = false
                    }) {
                        AddCashbackScreen(viewModel = koinViewModel()) {

                        }
                    }
                }
            }
        }
    }
}
