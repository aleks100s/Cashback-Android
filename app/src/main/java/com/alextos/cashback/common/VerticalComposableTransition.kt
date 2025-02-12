package com.alextos.cashback.common

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

inline fun <reified Route: Any> NavGraphBuilder.verticalComposableTransition(
    noinline content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit
) {
    composable<Route>(
        enterTransition = { slideInVertically(animationSpec = tween(500)) { it } },
        exitTransition = { fadeOut(animationSpec = tween(500)) },
        popEnterTransition = { fadeIn(animationSpec = tween(500)) },
        popExitTransition = { slideOutVertically(animationSpec = tween(500)) { it } } ,
        content = content
    )
}