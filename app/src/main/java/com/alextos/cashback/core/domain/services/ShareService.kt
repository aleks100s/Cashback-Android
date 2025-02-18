package com.alextos.cashback.core.domain.services

interface ShareService {
    fun share(link: String)
    fun shareApp()
}