package com.alextos.cashback.core.domain.repository

import com.alextos.cashback.core.domain.models.Place
import kotlinx.coroutines.flow.Flow

interface PlaceRepository {
    fun getPlaces(): Flow<List<Place>>
    fun getPlace(id: String): Flow<Place?>
    suspend fun deletePlace(place: Place)
    suspend fun createOrUpdate(place: Place)
    suspend fun replaceAll(places: List<Place>)
}