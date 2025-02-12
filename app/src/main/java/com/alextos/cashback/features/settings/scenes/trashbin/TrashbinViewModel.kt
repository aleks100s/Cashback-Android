package com.alextos.cashback.features.settings.scenes.trashbin

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class TrashbinViewModel: ViewModel() {
    private val _state = MutableStateFlow(TrashbinState())
    val state = _state.asStateFlow()
}