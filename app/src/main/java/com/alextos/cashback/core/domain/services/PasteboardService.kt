package com.alextos.cashback.core.domain.services

interface PasteboardService {
    fun copy(label: String, text: String)
}