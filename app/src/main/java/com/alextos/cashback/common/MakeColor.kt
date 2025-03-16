package com.alextos.cashback.common

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb

fun makeColor(hex: String?): Color {
    return Color(android.graphics.Color.parseColor(hex ?: "#E7E7E7"))
}

fun Color.toHex(): String {
    return String.format("%06X", this.toArgb() and 0xFFFFFF)
}