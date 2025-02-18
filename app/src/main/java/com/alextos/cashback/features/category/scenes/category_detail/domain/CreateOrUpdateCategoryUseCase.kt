package com.alextos.cashback.features.category.scenes.category_detail.domain

import com.alextos.cashback.core.domain.models.Category
import com.alextos.cashback.core.domain.repository.CategoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CreateOrUpdateCategoryUseCase(
    private val repository: CategoryRepository
) {
    suspend fun execute(
        category: Category?,
        categoryName: String,
        emoji: String,
        description: String
    ) {
        withContext(Dispatchers.IO) {
            val newEmoji = extractFirstEmoji(emoji) ?: categoryName.firstOrNull() ?: "?"
            val updatedCategory = category?.copy(
                name = categoryName,
                emoji = newEmoji.toString(),
                info = description
            ) ?: Category(
                name = categoryName,
                emoji = newEmoji.toString(),
                info = description,
                isNative = false
            )
            repository.createOrUpdate(updatedCategory)
        }
    }

    private fun extractFirstEmoji(input: String): String? {
        if (input.isEmpty()) return null
        val firstCodePoint = input.codePointAt(0)
        val charCount = Character.charCount(firstCodePoint) // Определяем длину символа в UTF-16
        return input.substring(0, charCount) // Берем нужное количество символов
    }
}