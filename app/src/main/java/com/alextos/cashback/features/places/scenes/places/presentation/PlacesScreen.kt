package com.alextos.cashback.features.places.scenes.places.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alextos.cashback.R
import com.alextos.cashback.common.UiText
import com.alextos.cashback.common.views.ContextMenuItem
import com.alextos.cashback.common.views.CustomLabel
import com.alextos.cashback.common.views.RoundedList
import com.alextos.cashback.common.views.Screen
import com.alextos.cashback.common.views.SearchBar
import com.alextos.cashback.core.domain.models.Place
import com.alextos.cashback.features.places.scenes.places.presentation.components.PlaceItemView

@Composable
fun PlacesScreen(
    modifier: Modifier = Modifier,
    viewModel: PlacesViewModel,
    onPlaceSelected: (Place) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Screen(
        modifier = modifier,
        title = stringResource(R.string.places_title),
        floatingActionButton = {
            Button(onClick = {
                viewModel.onAction(PlacesAction.AddPlace)
            }) {
                CustomLabel(
                    title = stringResource(R.string.places_add_place),
                    imageVector = Icons.Filled.Add
                )
            }
        }
    ) {
        PlacesView(
            modifier = it,
            state = state
        ) { action ->
            when (action) {
                is PlacesAction.PlaceSelected -> onPlaceSelected(action.place)
                else -> viewModel.onAction(action)
            }
        }
    }
}

@Composable
private fun PlacesView(
    modifier: Modifier = Modifier,
    state: PlacesState,
    onAction: (PlacesAction) -> Unit
) {
    if (state.isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else if (state.allPlaces.isEmpty()) {
        Box(
            modifier = Modifier
                .padding(horizontal = 32.dp)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = stringResource(R.string.places_empty_view_title),
                    tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)
                )

                Text(
                    text = stringResource(R.string.places_empty_view_title),
                    textAlign = TextAlign.Center
                )
            }
        }
    } else {
        val scrollState: LazyListState = rememberLazyListState()

        RoundedList(
            modifier = modifier.padding(horizontal = 16.dp),
            list = state.filteredPlaces,
            itemView = { modifier, place ->
                PlaceItemView(modifier, place) {
                    onAction(PlacesAction.FavouriteToggle(place))
                }
            },
            stickyHeader = {
                AnimatedVisibility(
                    visible = scrollState.lastScrolledBackward || !scrollState.canScrollBackward,
                    enter = fadeIn() + expandVertically(),
                    exit = fadeOut() + shrinkVertically()
                ) {
                    Column(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.background)
                            .padding(bottom = 4.dp)
                    ) {
                        SearchBar(
                            value = state.searchQuery,
                            placeholder = stringResource(R.string.places_search_placeholder)
                        ) {
                            onAction(PlacesAction.SearchQueryChanged(it))
                        }
                    }
                }
            },
            emptyView = {
                Box(
                    modifier = Modifier
                        .padding(horizontal = 32.dp)
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = stringResource(R.string.places_empty_view_title),
                            tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)
                        )

                        Text(
                            text = stringResource(R.string.places_empty_view_title),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            },
            onItemClick = {
                onAction(PlacesAction.PlaceSelected(it))
            },
            contextMenuActions = { place ->
                listOf(
                    ContextMenuItem(
                        title = UiText.StringResourceId(R.string.places_edit_button),
                        action = {
                            onAction(PlacesAction.EditPlace(it))
                        }
                    ),
                    ContextMenuItem(
                        title = UiText.StringResourceId(R.string.places_delete_button),
                        isDestructive = true,
                        action = {
                            onAction(PlacesAction.DeletePlace(it))
                        }
                    )
                )
            },
            allowSwipe = false
        )
    }
}
