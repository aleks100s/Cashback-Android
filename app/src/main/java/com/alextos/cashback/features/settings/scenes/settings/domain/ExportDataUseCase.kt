package com.alextos.cashback.features.settings.scenes.settings.domain

import com.alextos.cashback.core.data.dto.UserData
import com.alextos.cashback.core.data.dto.mappers.toDto
import com.alextos.cashback.core.domain.repository.CardsRepository
import com.alextos.cashback.core.domain.repository.CategoryRepository
import com.alextos.cashback.core.domain.services.UserDataService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ExportDataUseCase(
    private val categoryRepository: CategoryRepository,
    private val cardsRepository: CardsRepository,
    private val userDataService: UserDataService
) {
    suspend fun execute() {
        withContext(Dispatchers.IO) {
            val categories = categoryRepository.getAllCategoriesExport()
            val cards = cardsRepository.getCardsExport()
            val data = UserData(
                categories = categories.map { it.toDto() },
                cards = cards.map { it.toDto() },
                places = emptyList(),
                payments = emptyList()
            )
            userDataService.exportData(data)
        }
    }
}