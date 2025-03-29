package com.alextos.cashback.core.data.repository

import com.alextos.cashback.core.data.dao.PlaceDao
import com.alextos.cashback.core.data.entities.mappers.toDomain
import com.alextos.cashback.core.data.entities.mappers.toEntity
import com.alextos.cashback.core.domain.models.Place
import com.alextos.cashback.core.domain.repository.PlaceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PlaceRepositoryImpl(private val placeDao: PlaceDao): PlaceRepository {
    override fun getPlacesFlow(): Flow<List<Place>> {
        return placeDao.getAllFlow().map { list ->
            list.map { it.toDomain() }
        }
    }

    override fun getPlace(id: String): Flow<Place?> {
        return placeDao.getPlace(id).map { it?.toDomain() }
    }

    override suspend fun deletePlace(place: Place) {
        placeDao.delete(place.toEntity())
    }

    override suspend fun createOrUpdate(place: Place) {
        placeDao.upsert(place.toEntity())
    }

    override suspend fun replaceAll(places: List<Place>) {
        placeDao.replaceAll(places.map { it.toEntity() })
    }

    override suspend fun getPlaces(): List<Place> {
        return placeDao.getAll().map { it.toDomain() }
    }
}