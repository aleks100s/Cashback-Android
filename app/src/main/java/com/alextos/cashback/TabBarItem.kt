package com.alextos.cashback

import kotlinx.serialization.Serializable

@Serializable
sealed class TabBarItem(
    val title: String
) {
    @Serializable
    data object Cards: TabBarItem(title = "Мои карты")

    @Serializable
    data object Settings: TabBarItem(title = "Настройки")
}