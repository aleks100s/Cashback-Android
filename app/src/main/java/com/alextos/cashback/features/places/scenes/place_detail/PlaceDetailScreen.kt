package com.alextos.cashback.features.places.scenes.place_detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alextos.cashback.R
import com.alextos.cashback.common.ads.AdBannerView
import com.alextos.cashback.common.views.CustomButton
import com.alextos.cashback.common.views.FavouriteButton
import com.alextos.cashback.common.views.Screen
import com.alextos.cashback.common.views.SectionView
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
    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        SectionView(title = stringResource(R.string.place_detail_about_place)) {
            Row(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = state.placeName)

                Text(
                    text = stringResource(R.string.place_detail_place_name),
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                )
            }

            HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))

            Row(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = state.category?.name ?: "")

                Text(
                    text = stringResource(R.string.place_detail_category),
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                )
            }

            HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))

            Row(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (state.isFavourite) stringResource(R.string.place_detail_in_favourite) else stringResource(R.string.place_detail_add_to_favourite),
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                )

                FavouriteButton(state.isFavourite) {
                    onAction(PlaceDetailAction.ToggleFavourite)
                }
            }
        }

        if (!state.isEditMode) {
            SectionView(title = stringResource(R.string.place_detail_cards)) {

            }
        }
    }
}