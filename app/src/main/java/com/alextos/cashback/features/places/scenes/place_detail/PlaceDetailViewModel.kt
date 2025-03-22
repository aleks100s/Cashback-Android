package com.alextos.cashback.features.places.scenes.place_detail

import androidx.lifecycle.ViewModel
import com.alextos.cashback.core.domain.services.AppInfoService
import com.alextos.cashback.core.domain.services.AppInstallationSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class PlaceDetailViewModel(
    appInfoService: AppInfoService
): ViewModel() {
    private val _state = MutableStateFlow(PlaceDetailState())
    val state = _state.asStateFlow()

    val bannerId = when (appInfoService.installationSource) {
        AppInstallationSource.GOOGLE_PLAY -> "R-M-14460024-3"
        AppInstallationSource.HUAWEI -> "demo-banner-yandex"
        AppInstallationSource.RU_STORE -> "R-M-14164420-3"
    }

    fun onAction(action: PlaceDetailAction) {
        when (action) {
        }
    }
}