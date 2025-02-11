package com.alextos.cashback.core.domain.services

import com.alextos.cashback.common.UiText

interface ToastService {
    fun showToast(text: UiText)
}