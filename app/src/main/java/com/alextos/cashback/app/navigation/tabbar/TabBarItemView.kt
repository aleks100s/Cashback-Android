package com.alextos.cashback.app.navigation.tabbar

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun RowScope.TabBarItemView(
    tab: TabBarItem,
    isActive: Boolean,
    onClick: () -> Unit
) {
    NavigationBarItem(
        label = {
            Text(text = tab.title)
        },
        alwaysShowLabel = true,
        icon = {
            Icon(
                imageVector = when(tab) {
                    is TabBarItem.Cards -> Icons.Default.Email
                    is TabBarItem.Settings -> Icons.Default.Settings
                },
                contentDescription = tab.title
            )
        },
        selected = isActive,
        colors = NavigationBarItemColors(
            selectedIconColor = MaterialTheme.colorScheme.onPrimary,
            selectedTextColor = MaterialTheme.colorScheme.primary,
            unselectedIconColor = MaterialTheme.colorScheme.secondary,
            unselectedTextColor = MaterialTheme.colorScheme.secondary,
            selectedIndicatorColor = MaterialTheme.colorScheme.primary,
            disabledIconColor = MaterialTheme.colorScheme.onTertiary,
            disabledTextColor = MaterialTheme.colorScheme.tertiary
        ),
        onClick = onClick
    )
}