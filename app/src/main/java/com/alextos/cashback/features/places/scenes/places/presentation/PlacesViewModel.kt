package com.alextos.cashback.features.places.scenes.places.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.alextos.cashback.R
import com.alextos.cashback.common.UiText
import com.alextos.cashback.core.domain.repository.PlaceRepository
import com.alextos.cashback.core.domain.services.AnalyticsEvent
import com.alextos.cashback.core.domain.services.AnalyticsService
import com.alextos.cashback.core.domain.services.ToastService
import com.alextos.cashback.features.cards.CardsRoute
import com.alextos.cashback.features.places.PlacesRoute
import com.alextos.cashback.features.places.scenes.places.domain.FilterPlacesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PlacesViewModel(
    private val placeRepository: PlaceRepository,
    private val analyticsService: AnalyticsService,
    private val filterUseCase: FilterPlacesUseCase,
    private val toastService: ToastService
): ViewModel() {
    private val _state = MutableStateFlow(PlacesState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            placeRepository.getPlaces()
                .collect { list ->
                    _state.update {
                        it.copy(allPlaces = list, filteredPlaces = filterUseCase.execute(list, it.searchQuery))
                    }
                }
        }
    }

    fun onAction(action: PlacesAction) {
        when (action) {
            is PlacesAction.SearchQueryChanged -> {
                _state.update {
                    it.copy(searchQuery = action.query, filteredPlaces = filterUseCase.execute(it.allPlaces, action.query))
                }
            }
            is PlacesAction.DeletePlace -> {
                analyticsService.logEvent(AnalyticsEvent.PlacesDelete)
                viewModelScope.launch(Dispatchers.IO) {
                    placeRepository.deletePlace(action.place)
                }
            }
            is PlacesAction.EditPlace -> {
                analyticsService.logEvent(AnalyticsEvent.PlacesEdit)
            }
            is PlacesAction.FavouriteToggle -> {
                analyticsService.logEvent(AnalyticsEvent.PlacesFavouriteToggle)
                viewModelScope.launch(Dispatchers.IO) {
                    val place = action.place
                    placeRepository.createOrUpdate(place.copy(isFavourite = !place.isFavourite))
                }
                toastService.showToast(
                    UiText.StringResourceId(
                        if (!action.place.isFavourite) {
                            R.string.common_added_to_favourite
                        } else {
                            R.string.common_removed_from_favourite
                        }
                    )
                )
            }
            is PlacesAction.AddPlace -> {
                analyticsService.logEvent(AnalyticsEvent.PlacesAddPlaceButtonTapped)
            }
            is PlacesAction.PlaceSelected -> {
                analyticsService.logEvent(AnalyticsEvent.PlacesSelect)
            }
        }
    }
}