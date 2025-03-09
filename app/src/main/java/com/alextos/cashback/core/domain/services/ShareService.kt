package com.alextos.cashback.core.domain.services

sealed interface AppType {
    data object iOS: AppType
    data object RuStore: AppType
    data object GooglePlay: AppType
    data object HuaweiAppGallery: AppType
}

interface ShareService {
    fun share(link: String)
    fun shareApp(appType: AppType)
}