package com.alextos.cashback.features.category.di

import com.alextos.cashback.features.category.CategoryMediator
import com.alextos.cashback.features.category.CategoryMediatorImpl
import com.alextos.cashback.features.category.data.CategoryRepositoryImpl
import com.alextos.cashback.features.category.scenes.category_list.domain.CategoryRepository
import com.alextos.cashback.features.category.scenes.category_list.domain.ArchiveCategoryUseCase
import com.alextos.cashback.features.category.scenes.category_list.domain.FilterCategoryUseCase
import com.alextos.cashback.features.category.scenes.category_list.domain.IncreaseCategoryPriorityUseCase
import com.alextos.cashback.features.category.scenes.category_list.presentation.CategoryListViewModel
import com.alextos.cashback.features.category.scenes.category_detail.domain.ValidateCategoryUseCase
import com.alextos.cashback.features.category.scenes.category_detail.presentation.CategoryDetailViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val categoryModule = module {
    viewModelOf(::CategoryListViewModel)
    viewModelOf(::CategoryDetailViewModel)
    factory<CategoryRepository> { CategoryRepositoryImpl(get()) }
    factory { FilterCategoryUseCase() }
    factory { IncreaseCategoryPriorityUseCase(get()) }
    factory { ArchiveCategoryUseCase(get()) }
    factory { ValidateCategoryUseCase() }
    single<CategoryMediator> { CategoryMediatorImpl() }
}