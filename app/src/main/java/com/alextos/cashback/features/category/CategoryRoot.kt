package com.alextos.cashback.features.category

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.alextos.cashback.features.category.presentation.category_list.CategoryListScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun CategoryRoot(modifier: Modifier = Modifier) {
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
                CategoryListScreen(viewModel = koinViewModel()) {
                    navController.navigate(CategoryRoute.CreateCategory(it))
                }
            }

            composable<CategoryRoute.CreateCategory> {
                val args = it.toRoute<CategoryRoute.CreateCategory>()
                val name = args.name

                Text("Create category $name")
            }
        }
    }
}
