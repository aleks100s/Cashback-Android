package com.alextos.cashback.core.domain.models.currency

val Currency.localization: String
    get() = when (this) {
        Currency.RUBLE -> "Рубли"
        Currency.MILES -> "Мили"
        Currency.POINTS -> "Баллы"
    }