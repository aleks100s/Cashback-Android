package com.alextos.cashback.features.category

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.alextos.cashback.common.horizontalComposableTransition
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

            horizontalComposableTransition<CategoryRoute.CategoryDetail> {
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
