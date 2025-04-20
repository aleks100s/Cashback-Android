package com.alextos.cashback.core.data.services

import android.content.Context
import android.content.Intent
import androidx.core.content.FileProvider.*
import com.alextos.cashback.R
import com.alextos.cashback.core.data.dto.UserData
import com.alextos.cashback.core.data.dto.mappers.toDomain
import com.alextos.cashback.core.data.dto.mappers.toDto
import com.alextos.cashback.core.domain.repository.CardRepository
import com.alextos.cashback.core.domain.repository.CategoryRepository
import com.alextos.cashback.core.domain.repository.PaymentRepository
import com.alextos.cashback.core.domain.repository.PlaceRepository
import com.alextos.cashback.core.domain.services.UserDataDelegate
import com.alextos.cashback.core.domain.services.UserDataFileProvider
import com.alextos.cashback.core.domain.services.UserDataService
import kotlinx.serialization.json.Json
import java.io.File

class UserDataServiceImpl(
    private val context: Context,
    private val categoryRepository: CategoryRepository,
    private val cardRepository: CardRepository,
    private val placeRepository: PlaceRepository,
    private val paymentRepository: PaymentRepository
): UserDataService {
    override var delegate: UserDataDelegate? = null
    override var provider: UserDataFileProvider? = null

    override suspend fun exportData() {
        val data = prepareData()
        val json = Json.encodeToString(data)
        val file = createJsonFile(json)
        shareJsonFile(file)
    }

    private suspend fun prepareData(): UserData {
        val categories = categoryRepository.getAllCategories()
        val cards = cardRepository.getAllCards()
        val places = placeRepository.getPlaces()
        val payments = paymentRepository.getAllPayments()
        val data = UserData(
            categories = categories.map { it.toDto() },
            cards = cards.map { it.toDto() },
            places = places.map { it.toDto() },
            payments = payments.map { it.toDto() }
        )
        return data
    }

    private fun createJsonFile(jsonString: String): File {
        val file = File(context.getExternalFilesDir(null), "Кешбэк")
        file.writeText(jsonString)
        return file
    }

    private fun shareJsonFile(file: File) {
        val uri = getUriForFile(context, "${context.packageName}.fileprovider", file)

        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "application/json"
            putExtra(Intent.EXTRA_TITLE, "Кешбэк")
            putExtra(Intent.EXTRA_STREAM, uri)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }

        val chooser = Intent.createChooser(intent, context.getString(R.string.export_data_prompt))
        chooser.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(chooser)
    }

    override fun initiateImport() {
        provider?.showFilePicker()
    }

    override suspend fun continueImport(json: String) {
        val data: UserData = Json.decodeFromString(json)
        categoryRepository.replaceAll(data.categories.map { it.toDomain() })
        cardRepository.replaceAll(data.cards.map { it.toDomain() })
        placeRepository.replaceAll(data.places.map { it.toDomain() })
        paymentRepository.replaceAll(data.payments.map { it.toDomain() })
        delegate?.userDataServiceDidFinishImport()
    }
}