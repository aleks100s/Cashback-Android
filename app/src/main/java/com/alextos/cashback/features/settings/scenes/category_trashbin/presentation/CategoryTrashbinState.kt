package com.alextos.cashback.features.settings.scenes.category_trashbin.presentation

import com.alextos.cashback.core.domain.models.Category

data class CategoryTrashbinState(
    val categories: List<Category> = emptyList()
)
