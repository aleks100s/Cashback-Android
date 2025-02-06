package com.alextos.cashback.core.data.services

import android.app.Application
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import com.alextos.cashback.core.domain.services.PasteboardService

class PasteboardServiceImpl(
    private val application: Application
): PasteboardService {
    override fun copy(label: String, text: String) {
        val clipboard = application.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText(label, text)
        clipboard.setPrimaryClip(clip)
    }
}