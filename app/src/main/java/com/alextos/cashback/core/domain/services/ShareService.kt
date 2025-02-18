package com.alextos.cashback.core.domain.services

sealed interface AppType {
    data object iOS: AppType
    data object Android: AppType
}

interface ShareService {
    fun share(link: String)
    fun shareApp(appType: AppType)
}