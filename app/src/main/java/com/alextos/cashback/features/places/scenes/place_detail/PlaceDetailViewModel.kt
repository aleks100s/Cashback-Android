package com.alextos.cashback.features.places.scenes.place_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.alextos.cashback.core.domain.models.Place
import com.alextos.cashback.core.domain.repository.PlaceRepository
import com.alextos.cashback.core.domain.services.AnalyticsEvent
import com.alextos.cashback.core.domain.services.AnalyticsService
import com.alextos.cashback.core.domain.services.AppInfoService
import com.alextos.cashback.core.domain.services.AppInstallationSource
import com.alextos.cashback.features.places.PlacesRoute
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PlaceDetailViewModel(
    savedStateHandle: SavedStateHandle,
    appInfoService: AppInfoService,
    private val placeRepository: PlaceRepository,
    private val analyticsService: AnalyticsService
): ViewModel() {
    private val placeId = savedStateHandle.toRoute<PlacesRoute.PlaceDetails>().placeId

    private val _state = MutableStateFlow(PlaceDetailState())
    val state = _state.asStateFlow()

    val bannerId = when (appInfoService.installationSource) {
        AppInstallationSource.GOOGLE_PLAY -> "R-M-14460024-3"
        AppInstallationSource.HUAWEI -> "demo-banner-yandex"
        AppInstallationSource.RU_STORE -> "R-M-14164420-3"
    }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            placeRepository.getPlace(id = placeId)
                .collect { place ->
                    _state.update { state ->
                        state.copy(
                            placeName = place?.name ?: "",
                            category = place?.category,
                            isFavourite = place?.isFavourite ?: false
                        )
                    }
                }
        }
    }

    fun onAction(action: PlaceDetailAction) {
        when (action) {
            is PlaceDetailAction.ToggleEditMode -> {
                if (state.value.isEditMode) {
                    analyticsService.logEvent(AnalyticsEvent.PlaceDetailDoneButtonTapped)
                    viewModelScope.launch(Dispatchers.IO) {
                        state.value.category?.let {
                            val place = Place(
                                id = placeId,
                                name = state.value.placeName,
                                category = it,
                                isFavourite = state.value.isFavourite
                            )
                            placeRepository.createOrUpdate(place)
                        }
                    }
                } else {
                    analyticsService.logEvent(AnalyticsEvent.PlaceDetailEditButtonTapped)
                }
                _state.update { it.copy(isEditMode = !state.value.isEditMode) }
            }
        }
    }
}