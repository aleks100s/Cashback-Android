package com.alextos.cashback.app.navigation.tabbar

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
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
            Text(text = tab.title.asString())
        },
        alwaysShowLabel = true,
        icon = {
            Icon(
                imageVector = when(tab) {
                    is TabBarItem.Cards -> Icons.Default.Home
                    is TabBarItem.Settings -> Icons.Default.Settings
                    is TabBarItem.Categories -> Icons.Default.List
                    is TabBarItem.Places -> Icons.Default.LocationOn
                    is TabBarItem.Payments -> Icons.Default.ShoppingCart
                },
                contentDescription = tab.title.asString()
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