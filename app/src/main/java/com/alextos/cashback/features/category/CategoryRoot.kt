package com.alextos.cashback.features.category

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.alextos.cashback.features.category.scenes.category_list.presentation.CategoryListScreen
import com.alextos.cashback.features.category.scenes.category_detail.presentation.CategoryDetailScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun CategoryRoot(
    modifier: Modifier = Modifier,
    onSelectCategory: () -> Unit,
    goBack: () -> Unit
) {
    val navController = rememberNavController()

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = CategoryRoute.CategoryGraph
    ) {
        navigation<CategoryRoute.CategoryGraph>(
            startDestination = CategoryRoute.CategoryList
        ) {
            composable<CategoryRoute.CategoryList> {
                CategoryListScreen(
                    viewModel = koinViewModel(),
                    onSelectCategory = onSelectCategory,
                    goBack = goBack,
                    onCreateCategory = {
                        navController.navigate(CategoryRoute.CategoryDetail(it, null))
                    },
                    onEditCategory = {
                        navController.navigate(CategoryRoute.CategoryDetail(null, it))
                    }
                )
            }

            composable<CategoryRoute.CategoryDetail>(
                enterTransition = { slideInHorizontally(animationSpec = tween(500)) { it } },
                exitTransition = { fadeOut(animationSpec = tween(500)) },
                popEnterTransition = { fadeIn(animationSpec = tween(500)) },
                popExitTransition = { slideOutHorizontally(animationSpec = tween(500)) { it } }
            ) {
                CategoryDetailScreen(
                    viewModel = koinViewModel(),
                    goBack = {
                        navController.popBackStack()
                    }
                ) {
                    navController.popBackStack()
                }
            }
        }
    }
}
