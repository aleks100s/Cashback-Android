package com.alextos.cashback.features.places.di

import com.alextos.cashback.features.places.scenes.places.domain.FilterPlacesUseCase
import com.alextos.cashback.features.places.scenes.places.presentation.PlacesViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val placesModule = module {
    viewModelOf(::PlacesViewModel)
    factory { FilterPlacesUseCase() }
}