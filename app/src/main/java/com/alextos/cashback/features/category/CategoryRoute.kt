package com.alextos.cashback.features.category

import kotlinx.serialization.Serializable

interface CategoryRoute {
    @Serializable
    data object CategoryGraph

    @Serializable
    data object CategoryList

    @Serializable
    data class CategoryDetail(val name: String?, val categoryId: String?)
}