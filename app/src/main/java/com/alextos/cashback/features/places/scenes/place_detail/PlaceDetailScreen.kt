package com.alextos.cashback.features.places.scenes.place_detail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alextos.cashback.common.ads.AdBannerView
import com.alextos.cashback.common.views.Screen

@Composable
fun PlaceDetailScreen(
    modifier: Modifier = Modifier,
    viewModel: PlaceDetailViewModel
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Screen(
        modifier = modifier,
        title = state.title,
        bannerView = {
            if (state.isAdVisible) {
                AdBannerView(id = viewModel.bannerId)
            }
        }
    ) {
        PlaceDetailView(
            modifier = it,
            state = state,
            onAction = { action ->
                when(action) {
                    else -> viewModel.onAction(action)
                }
            }
        )
    }
}

@Composable
private fun PlaceDetailView(
    modifier: Modifier,
    state: PlaceDetailState,
    onAction: (PlaceDetailAction) -> Unit
) {

}