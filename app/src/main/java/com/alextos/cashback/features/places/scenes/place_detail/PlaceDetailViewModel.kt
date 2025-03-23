package com.alextos.cashback.features.places.scenes.place_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.alextos.cashback.core.domain.models.Place
import com.alextos.cashback.core.domain.repository.CardRepository
import com.alextos.cashback.core.domain.repository.PlaceRepository
import com.alextos.cashback.core.domain.services.AnalyticsEvent
import com.alextos.cashback.core.domain.services.AnalyticsService
import com.alextos.cashback.core.domain.services.AppInfoService
import com.alextos.cashback.core.domain.services.AppInstallationSource
import com.alextos.cashback.core.domain.settings.SettingsManager
import com.alextos.cashback.features.category.CategoryMediator
import com.alextos.cashback.features.places.PlacesRoute
import com.alextos.cashback.features.places.scenes.place_detail.components.PlaceCard
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID

class PlaceDetailViewModel(
    savedStateHandle: SavedStateHandle,
    appInfoService: AppInfoService,
    private val placeRepository: PlaceRepository,
    private val analyticsService: AnalyticsService,
    private val categoryMediator: CategoryMediator,
    private val settingsManager: SettingsManager,
    private val cardRepository: CardRepository
): ViewModel() {
    private val placeId = savedStateHandle.toRoute<PlacesRoute.PlaceDetails>().placeId

    private val _state = MutableStateFlow(PlaceDetailState(isEditMode = savedStateHandle.toRoute<PlacesRoute.PlaceDetails>().isEditMode))
    val state = _state.asStateFlow()

    val bannerId = when (appInfoService.installationSource) {
        AppInstallationSource.GOOGLE_PLAY -> "R-M-14460024-3"
        AppInstallationSource.HUAWEI -> "demo-banner-yandex"
        AppInstallationSource.RU_STORE -> "R-M-14164420-3"
    }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            placeId?.let {
                placeRepository.getPlace(id = it)
                    .distinctUntilChanged()
                    .collect { place ->
                        _state.update { state ->
                            state.copy(
                                placeName = place?.name ?: "",
                                category = place?.category,
                                isFavourite = place?.isFavourite ?: false,
                            )
                        }
                    }
            } ?: run {
                _state.update { it.copy(isCreateMode = true) }
            }
        }

        viewModelScope.launch(Dispatchers.IO) {
            categoryMediator.selectedCategory
                .collect { category ->
                    onAction(PlaceDetailAction.CategorySelected(category))
                }
        }

        viewModelScope.launch(Dispatchers.IO) {
            settingsManager.isAdEnabled
                .collect { isEnabled ->
                    _state.update { it.copy(isAdVisible = isEnabled) }
                }
        }

        viewModelScope.launch(Dispatchers.IO) {
            _state.collect { state ->
                state.category?.let { category ->
                    val cards = cardRepository.getCards(category).mapNotNull { card ->
                        card.cashback.firstOrNull { it.category == category }?.let {
                            PlaceCard(card = card, cashback = it)
                        }
                    }
                    _state.update { it.copy(cards = cards) }
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
                        makePlace()?.let { place ->
                            placeRepository.createOrUpdate(place)
                        }
                    }
                } else {
                    analyticsService.logEvent(AnalyticsEvent.PlaceDetailEditButtonTapped)
                }
                _state.update { it.copy(isEditMode = !state.value.isEditMode) }
            }

            is PlaceDetailAction.ToggleFavourite -> {
                analyticsService.logEvent(AnalyticsEvent.PlaceDetailToggleFavourite)
                viewModelScope.launch(Dispatchers.IO) {
                    makePlace()?.let { place ->
                        placeRepository.createOrUpdate(place.copy(isFavourite = !place.isFavourite))
                    }
                }
            }

            is PlaceDetailAction.ChangeName -> {
                _state.update { it.copy(placeName = action.name) }
            }

            is PlaceDetailAction.SelectCategory -> {
                if (state.value.isEditMode) {
                    analyticsService.logEvent(AnalyticsEvent.PlaceDetailSelectCategoryButtonTapped)
                } else if (state.value.isCreateMode) {
                    analyticsService.logEvent(AnalyticsEvent.AddPlaceSelectCategoryButtonTapped)
                }
            }

            is PlaceDetailAction.CategorySelected -> {
                _state.update { it.copy(category = action.category) }
            }

            is PlaceDetailAction.SavePlace -> {
                analyticsService.logEvent(AnalyticsEvent.AddPlaceSaveButtonTapped)
                viewModelScope.launch(Dispatchers.IO) {
                    makePlace()?.let { place ->
                        placeRepository.createOrUpdate(place)
                    }
                }
            }

            is PlaceDetailAction.DeletePlace -> {
                viewModelScope.launch(Dispatchers.IO) {
                    makePlace()?.let {
                        placeRepository.deletePlace(it)
                    }
                }
            }
        }
    }

    private fun makePlace(): Place? {
        state.value.category?.let {
            return Place(
                id = placeId ?: UUID.randomUUID().toString(),
                name = state.value.placeName.trim(),
                category = it,
                isFavourite = state.value.isFavourite
            )
        }

        return null
    }
}