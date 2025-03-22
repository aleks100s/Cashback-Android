package com.alextos.cashback.features.places.scenes.place_detail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alextos.cashback.R
import com.alextos.cashback.common.ads.AdBannerView
import com.alextos.cashback.common.views.CustomButton
import com.alextos.cashback.common.views.Screen
import com.alextos.cashback.features.cards.scenes.card_detail.presentation.CardDetailAction

@Composable
fun PlaceDetailScreen(
    modifier: Modifier = Modifier,
    viewModel: PlaceDetailViewModel,
    goBack: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val haptic = LocalHapticFeedback.current

    Screen(
        modifier = modifier,
        title = state.placeName,
        goBack = goBack,
        bannerView = {
            if (state.isAdVisible) {
                AdBannerView(id = viewModel.bannerId)
            }
        },
        actions = {
            CustomButton(
                title = if (state.isEditMode) {
                    stringResource(R.string.common_save)
                } else {
                    stringResource(R.string.common_edit)
                }
            ) {
                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                viewModel.onAction(PlaceDetailAction.ToggleEditMode)
            }
        },
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