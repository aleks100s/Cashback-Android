package com.alextos.cashback.core.data.services

import android.content.Context
import android.widget.Toast
import com.alextos.cashback.core.domain.services.ToastService
import com.alextos.cashback.util.UiText

class ToastServiceImpl(private val context: Context): ToastService {
    override fun showToast(text: UiText) {
        when (text) {
            is UiText.DynamicString -> {
                Toast.makeText(context, text.value, Toast.LENGTH_SHORT).show()
            }
            is UiText.StringResourceId -> {
                Toast.makeText(context, text.id, Toast.LENGTH_SHORT).show()
            }
        }

    }
}