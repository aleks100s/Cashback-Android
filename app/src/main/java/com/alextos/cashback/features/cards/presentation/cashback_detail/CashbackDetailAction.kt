package com.alextos.cashback.features.cards.presentation.cashback_detail

import com.alextos.cashback.core.domain.models.Category

sealed interface CashbackDetailAction {
    data class ChangePercent(val value: String): CashbackDetailAction
    data object SelectCategory: CashbackDetailAction
    data object SaveCashbackDetail: CashbackDetailAction
    data class CategorySelected(val category: Category): CashbackDetailAction
}