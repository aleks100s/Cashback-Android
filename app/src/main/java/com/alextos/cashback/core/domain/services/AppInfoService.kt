package com.alextos.cashback.core.domain.services

interface AppInfoService {
    val versionName: String
    val versionCode: Long
    val installationSource: AppInstallationSource
}

enum class AppInstallationSource {
    GOOGLE_PLAY,
    HUAWEI,
    RU_STORE
}