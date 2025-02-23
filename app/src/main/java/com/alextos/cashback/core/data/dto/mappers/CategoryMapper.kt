package com.alextos.cashback.core.data.dto.mappers

import com.alextos.cashback.core.data.dto.CategoryDto
import com.alextos.cashback.core.domain.models.Category

fun Category.toDto(): CategoryDto {
    return CategoryDto(
        id = id,
        name = name,
        emoji = emoji,
        synonyms = synonyms,
        priority = priority,
        isArchived = isArchived,
        isNative = isNative,
        info = info
    )
}

fun CategoryDto.toDomain(): Category {
    return Category(
        id = id,
        name = name,
        emoji = emoji,
        synonyms = synonyms,
        priority = priority,
        isArchived = isArchived,
        isNative = isNative,
        info = info
    )
}