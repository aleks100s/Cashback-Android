package com.alextos.cashback.features.places

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.alextos.cashback.common.transitions.horizontalComposableTransition
import com.alextos.cashback.features.places.scenes.place_detail.PlaceDetailScreen
import com.alextos.cashback.features.places.scenes.places.presentation.PlacesScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun PlacesRoot(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = PlacesRoute.PlacesGraph
    ) {
        navigation<PlacesRoute.PlacesGraph>(
            startDestination = PlacesRoute.Places
        ) {
            composable<PlacesRoute.Places> {
                PlacesScreen(
                    viewModel = koinViewModel(),
                    onPlaceSelected = { place ->
                        navController.navigate(PlacesRoute.PlaceDetails(place.id))
                    }
                )
            }

            horizontalComposableTransition<PlacesRoute.PlaceDetails> { navBackStackEntry ->
                val args = navBackStackEntry.toRoute<PlacesRoute.PlaceDetails>()
                val id = args.placeId

                PlaceDetailScreen(
                    viewModel = koinViewModel(),
                    goBack = { navController.popBackStack() },
                )
            }
        }
    }
}