package com.alextos.cashback.core.data.services

import android.content.Context
import com.alextos.cashback.core.domain.services.AppInfoService

class AppInfoServiceImpl(
    private val context: Context
): AppInfoService {
    override val versionName: String
        get() = context.packageManager.getPackageInfo(context.packageName, 0).versionName ?: ""

    override val versionCode: Long
        get() = context.packageManager.getPackageInfo(context.packageName, 0).longVersionCode
}