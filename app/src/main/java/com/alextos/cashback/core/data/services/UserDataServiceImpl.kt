package com.alextos.cashback.core.data.services

import android.content.Context
import android.content.Intent
import androidx.core.content.FileProvider.*
import com.alextos.cashback.R
import com.alextos.cashback.core.data.dto.UserData
import com.alextos.cashback.core.domain.services.UserDataService
import kotlinx.serialization.json.Json
import java.io.File

class UserDataServiceImpl(private val context: Context): UserDataService {
    override suspend fun exportData(data: UserData) {
        val json = Json.encodeToString(data)
        val file = createJsonFile(json)
        shareJsonFile(file)
    }

    private fun createJsonFile(jsonString: String): File {
        val file = File(context.getExternalFilesDir(null), "Кэшбэк")
        file.writeText(jsonString)
        return file
    }

    private fun shareJsonFile(file: File) {
        val uri = getUriForFile(context, "${context.packageName}.fileprovider", file)

        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "application/json"
            putExtra(Intent.EXTRA_TITLE, "Кэшбэк")
            putExtra(Intent.EXTRA_STREAM, uri)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }

        val chooser = Intent.createChooser(intent, context.getString(R.string.export_data_prompt))
        chooser.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(chooser)
    }
}