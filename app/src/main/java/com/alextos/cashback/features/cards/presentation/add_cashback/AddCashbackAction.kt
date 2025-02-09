package com.alextos.cashback.features.cards.presentation.add_cashback

import com.alextos.cashback.core.domain.models.Category

sealed interface AddCashbackAction {
    data class ChangePercent(val value: Double): AddCashbackAction
    data object SelectCategory: AddCashbackAction
    data object SaveCashback: AddCashbackAction
    data class CategorySelected(val category: Category): AddCashbackAction
}