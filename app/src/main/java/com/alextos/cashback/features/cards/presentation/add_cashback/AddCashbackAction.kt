package com.alextos.cashback.features.cards.presentation.add_cashback

sealed interface AddCashbackAction {
    data class ChangePercent(val value: Double): AddCashbackAction
    data object SelectCategory: AddCashbackAction
    data object SaveCashback: AddCashbackAction
}