package com.alextos.cashback.core.data.mappers

import com.alextos.cashback.core.data.entities.CategoryEntity
import com.alextos.cashback.core.domain.models.Category
import java.util.UUID

fun Category.toEntity(): CategoryEntity {
    return CategoryEntity(
        id = id.toString(),
        name = name,
        emoji = emoji,
        synonyms = synonyms,
        priority = priority,
        isArchived = isArchived,
        info = info,
        isNative = isNative
    )
}

fun CategoryEntity.toDomain(): Category {
    return Category(
        id = UUID.fromString(id),
        name = name,
        emoji = emoji,
        synonyms = synonyms,
        priority = priority,
        isArchived = isArchived,
        info = info,
        isNative = isNative
    )
}