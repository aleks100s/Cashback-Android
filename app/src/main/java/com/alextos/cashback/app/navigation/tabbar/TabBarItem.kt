package com.alextos.cashback.app.navigation.tabbar

import com.alextos.cashback.R
import com.alextos.cashback.common.UiText
import kotlinx.serialization.Serializable

@Serializable
sealed class TabBarItem(
    val title: UiText
) {
    @Serializable
    data object Cards: TabBarItem(title = UiText.StringResourceId(R.string.tab_bar_cards))

    @Serializable
    data object Settings: TabBarItem(title = UiText.StringResourceId(R.string.tab_bar_settings))
}