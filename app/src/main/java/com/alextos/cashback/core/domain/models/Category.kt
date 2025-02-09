package com.alextos.cashback.core.domain.models

import android.os.Parcel
import android.os.Parcelable
import com.alextos.cashback.core.presentation.views.ListElement
import kotlinx.serialization.Serializable
import java.util.UUID
import kotlin.random.Random

data class Category(
    override val id: String = UUID.randomUUID().toString(),
    val name: String,
    val emoji: String,
    val synonyms: String?,
    val priority: Int,
    val isArchived: Boolean,
    val info: String?,
    val isNative: Boolean
): ListElement, Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString(),
        parcel.readInt(),
        parcel.readByte() != 0.toByte(),
        parcel.readString(),
        parcel.readByte() != 0.toByte()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeString(emoji)
        parcel.writeString(synonyms)
        parcel.writeInt(priority)
        parcel.writeByte(if (isArchived) 1 else 0)
        parcel.writeString(info)
        parcel.writeByte(if (isNative) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Category> {
        override fun createFromParcel(parcel: Parcel): Category {
            return Category(parcel)
        }

        override fun newArray(size: Int): Array<Category?> {
            return arrayOfNulls(size)
        }
    }
}

fun generateMockCategory(): Category {
    val names = listOf("Books", "Music", "Cinema", "Fitness", "Pharmacy", "Electronics", "Restaurants")
    val emojis = listOf("📚", "🎵", "🎬", "🏃‍♂️", "💊", "📱", "🍽️")
    val synonymsList = listOf("Literature, Reading", "Songs, Audio", "Movies, Theater", "Gym, Sport", "Medicine, Health", "Gadgets, Tech", "Food, Dining", null)

    val index = Random.nextInt(names.size) // Выбираем случайный индекс

    return Category(
        id = UUID.randomUUID().toString(),
        name = names[index],
        emoji = emojis[index],
        synonyms = synonymsList[index],
        priority = Random.nextInt(1, 101), // Генерируем случайный приоритет от 1 до 100
        isArchived = false,
        info = null,
        isNative = true
    )
}
