package com.alextos.cashback.core.domain.models.currency

enum class Currency() {
    RUBLE,
    MILES,
    POINTS;

    override fun toString(): String {
        return localization
    }
}