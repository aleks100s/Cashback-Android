package com.alextos.cashback.core.domain.models.currency

enum class Currency {
    RUBLE,
    MILES,
    POINTS;

    override fun toString(): String {
        return localization
    }

    companion object {
        fun makeFrom(string: String): Currency {
            return when (string) {
                RUBLE.localization -> {
                    RUBLE
                }
                MILES.localization -> {
                    MILES
                }
                POINTS.localization -> {
                    POINTS
                }
                else -> RUBLE
            }
        }
    }
}