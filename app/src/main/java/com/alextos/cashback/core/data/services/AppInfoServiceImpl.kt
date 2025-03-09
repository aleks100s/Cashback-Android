package com.alextos.cashback.core.data.services

import android.content.Context
import android.os.Build
import com.alextos.cashback.core.domain.services.AppInfoService
import com.alextos.cashback.core.domain.services.AppInstallationSource


class AppInfoServiceImpl(
    private val context: Context
): AppInfoService {
    override val versionName: String
        get() = context.packageManager.getPackageInfo(context.packageName, 0).versionName ?: ""

    override val versionCode: Long
        get() = context.packageManager.getPackageInfo(context.packageName, 0).longVersionCode

    override val installationSource: AppInstallationSource
        get() = when (getInstallerPackageName(context, context.packageName)) {
            "com.android.vending" -> AppInstallationSource.GOOGLE_PLAY
            "com.huawei.appmarket" -> AppInstallationSource.HUAWEI
            else -> AppInstallationSource.RU_STORE
        }

    private fun getInstallerPackageName(context: Context, packageName: String): String? {
        return kotlin.runCatching {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)
                context.packageManager.getInstallSourceInfo(packageName).installingPackageName
            else
                @Suppress("DEPRECATION")
                context.packageManager.getInstallerPackageName(packageName)
        }.getOrNull()
    }
}