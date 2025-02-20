package com.alextos.cashback.core.data.services

import android.content.Context
import android.content.Intent
import com.alextos.cashback.R
import com.alextos.cashback.core.AppConstants
import com.alextos.cashback.core.domain.services.AppType
import com.alextos.cashback.core.domain.services.ShareService

class ShareServiceImpl(
    private val context: Context
): ShareService {
    override fun share(link: String) {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, link)
        }
        val chooser = Intent.createChooser(intent, context.getString(R.string.share_app_link))
        chooser.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(chooser)
    }

    override fun shareApp(appType: AppType) {
        val link = when(appType) {
            is AppType.Android -> AppConstants.RU_STORE_LINK
            is AppType.iOS -> AppConstants.APP_STORE_LINK
        }
        share(link = link)
    }
}