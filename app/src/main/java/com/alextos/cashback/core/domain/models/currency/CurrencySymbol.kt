package com.alextos.cashback.core.domain.models.currency

val Currency.symbol: String
    get() = when (this) {
        Currency.RUBLE -> "₽"
        Currency.MILES -> "М"
        Currency.POINTS -> "Б"
    }