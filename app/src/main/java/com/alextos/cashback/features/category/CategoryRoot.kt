package com.alextos.cashback.features.category

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.alextos.cashback.features.category.scenes.category_list.presentation.CategoryListScreen
import com.alextos.cashback.features.category.scenes.create_category.presentation.CreateCategoryScreen
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryRoot(modifier: Modifier = Modifier, onSelectCategory: () -> Unit) {
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
                    onSelectCategory = onSelectCategory
                )
            }

            composable<CategoryRoute.CreateCategory> {
                val args = it.toRoute<CategoryRoute.CreateCategory>()
                val name = args.name

                Text("Create category $name")
            }
        }
    }
}
