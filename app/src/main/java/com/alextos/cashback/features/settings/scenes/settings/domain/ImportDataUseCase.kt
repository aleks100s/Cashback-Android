package com.alextos.cashback.features.settings.scenes.settings.domain

import com.alextos.cashback.core.domain.repository.CardsRepository
import com.alextos.cashback.core.domain.repository.CategoryRepository
import com.alextos.cashback.core.domain.services.UserDataService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ImportDataUseCase(
    private val cardsRepository: CardsRepository,
    private val categoryRepository: CategoryRepository,
    userDataService: UserDataService
) {
    suspend fun execute() {
        withContext(Dispatchers.IO) {

        }
    }
}