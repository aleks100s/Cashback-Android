package com.alextos.cashback.common

import androidx.compose.ui.graphics.Color

fun makeColor(hex: String?): Color {
    return Color(android.graphics.Color.parseColor(hex ?: "#E7E7E7"))
}