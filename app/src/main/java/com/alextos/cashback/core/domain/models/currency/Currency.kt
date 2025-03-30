package com.alextos.cashback.core.domain.models.currency

import com.alextos.cashback.common.views.PickerElement

enum class Currency: PickerElement {
    RUBLE,
    MILES,
    POINTS;

    override val pickerText: String
        get() = localization

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