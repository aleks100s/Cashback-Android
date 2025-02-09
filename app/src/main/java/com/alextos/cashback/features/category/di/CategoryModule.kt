package com.alextos.cashback.features.category.di

import com.alextos.cashback.features.category.CategoryMediator
import com.alextos.cashback.features.category.CategoryMediatorImpl
import com.alextos.cashback.features.category.data.CategoryRepositoryImpl
import com.alextos.cashback.features.category.domain.CategoryRepository
import com.alextos.cashback.features.category.domain.FilterCategoryUseCase
import com.alextos.cashback.features.category.presentation.category_list.CategoryListViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val categoryModule = module {
    viewModelOf(::CategoryListViewModel)
    factory<CategoryRepository> { CategoryRepositoryImpl(get()) }
    factory { FilterCategoryUseCase() }
    single<CategoryMediator> { CategoryMediatorImpl() }
}