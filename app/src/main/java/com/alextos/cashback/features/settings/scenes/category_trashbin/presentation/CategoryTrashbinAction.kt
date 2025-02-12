package com.alextos.cashback.features.settings.scenes.category_trashbin.presentation

import com.alextos.cashback.core.domain.models.Category

sealed interface CategoryTrashbinAction {
    data class RestoreCategory(val category: Category) : CategoryTrashbinAction
    data object RestoreAll : CategoryTrashbinAction
}