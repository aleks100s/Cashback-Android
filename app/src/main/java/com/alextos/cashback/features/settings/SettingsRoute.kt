package com.alextos.cashback.features.settings

import kotlinx.serialization.Serializable

sealed interface SettingsRoute {
    @Serializable
    data object SettingsGraph: SettingsRoute

    @Serializable
    data object Settings: SettingsRoute

    @Serializable
    data object CategoryCatalog: SettingsRoute

    @Serializable
    data object Trashbin: SettingsRoute
}