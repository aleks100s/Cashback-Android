package com.alextos.cashback.features.places.scenes.place_detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alextos.cashback.R
import com.alextos.cashback.common.ads.AdBannerView
import com.alextos.cashback.common.views.CustomButton
import com.alextos.cashback.common.views.CustomDivider
import com.alextos.cashback.common.views.CustomLabel
import com.alextos.cashback.common.views.CustomWideButton
import com.alextos.cashback.common.views.EmptySearchView
import com.alextos.cashback.common.views.FavouriteButton
import com.alextos.cashback.common.views.Screen
import com.alextos.cashback.common.views.SectionView
import com.alextos.cashback.features.places.scenes.place_detail.components.PlaceCardView

@Composable
fun PlaceDetailScreen(
    modifier: Modifier = Modifier,
    viewModel: PlaceDetailViewModel,
    goBack: () -> Unit,
    onCategorySelect: () -> Unit,
    onSave: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val haptic = LocalHapticFeedback.current

    Screen(
        modifier = modifier,
        title = if (state.isCreateMode) stringResource(R.string.add_place_title) else state.placeName,
        goBack = goBack,
        actions = {
            if (!state.isCreateMode) {
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
            }
        },
        floatingActionButton = {
            if (state.isCreateMode) {
                Button(
                    onClick = {
                        viewModel.onAction(PlaceDetailAction.SavePlace)
                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                        onSave()
                    },
                    enabled = state.placeName.isNotEmpty() && state.category != null
                ) {
                    CustomLabel(
                        title = stringResource(R.string.common_save),
                        imageVector = Icons.Default.Done
                    )
                }
            }
        }
    ) {
        PlaceDetailView(
            modifier = it,
            state = state,
            onAction = { action ->
                when(action) {
                    is PlaceDetailAction.SelectCategory -> onCategorySelect()
                    else -> {}
                }
                viewModel.onAction(action)
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
                    .padding(horizontal = 16.dp, vertical = 10.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = state.placeName,
                    onValueChange = { onAction(PlaceDetailAction.ChangeName(it)) },
                    label = { Text(text = stringResource(R.string.place_detail_place_name)) },
                    shape = MaterialTheme.shapes.small,
                    enabled = state.isEditMode || state.isCreateMode
                )
            }

            Row(
                modifier = Modifier
                    .padding(end = 16.dp)
                    .padding(vertical = 8.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                CustomButton(
                    title = state.category?.name ?: stringResource(R.string.add_place_select_category),
                    enabled = state.isEditMode || state.isCreateMode
                ) {
                    onAction(PlaceDetailAction.SelectCategory)
                }

                if (state.category != null) {
                    Text(
                        text = stringResource(R.string.place_detail_category),
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            if (!state.isCreateMode) {
                CustomDivider()

                Row(
                    modifier = Modifier
                        .padding(start = 16.dp, end = 4.dp)
                        .padding(vertical = 4.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (state.isFavourite) stringResource(R.string.place_detail_in_favourite) else stringResource(
                            R.string.place_detail_add_to_favourite
                        ),
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                    )

                    FavouriteButton(isFavourite = state.isFavourite) {
                        onAction(PlaceDetailAction.ToggleFavourite)
                    }
                }
            }
        }

        if (!state.isEditMode && !state.isCreateMode) {
            if (state.cards.isEmpty()) {
                EmptySearchView(title = stringResource(R.string.place_detail_no_cards))
            } else {
                SectionView(title = stringResource(R.string.place_detail_cards)) {
                    Column(Modifier.fillMaxWidth()) {
                        state.cards.forEach { card ->
                            PlaceCardView(card)

                            if (card != state.cards.lastOrNull()) {
                                CustomDivider()
                            }
                        }
                    }
                }
            }
        }

        if (state.isEditMode) {
            SectionView {
                CustomWideButton(
                    title = stringResource(R.string.place_detail_delete),
                    color = MaterialTheme.colorScheme.error
                ) {
                    onAction(PlaceDetailAction.DeletePlace)
                }
            }
        }
    }
}