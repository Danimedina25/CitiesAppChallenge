package com.danifitdev.citiesappchallenge.cities.data.local

import android.database.sqlite.SQLiteFullException
import com.danifitdev.citiesappchallenge.cities.data.local.dao.CitiesDao
import com.danifitdev.citiesappchallenge.cities.data.mappers.toDomain
import com.danifitdev.citiesappchallenge.cities.data.mappers.toEntity
import com.danifitdev.citiesappchallenge.cities.domain.datasources.CitiesLocalDataSource
import com.danifitdev.citiesappchallenge.cities.domain.datasources.CityId
import com.danifitdev.citiesappchallenge.cities.domain.model.CityModel
import com.danifitdev.citiesappchallenge.core.domain.util.DataError
import com.danifitdev.citiesappchallenge.core.domain.util.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class CitiesLocalDataSourceImpl @Inject constructor
    (private val citiesDao: CitiesDao): CitiesLocalDataSource {
    override fun getCities(): Flow<List<CityModel>> {
        return citiesDao.getCities().map { cityEntities ->
            cityEntities.map { it.toDomain() }
        }
    }

    override suspend fun upserCity(cityModel: CityModel): Result<CityId, DataError.Local> {
        TODO("Not yet implemented")
    }

    override suspend fun upsertCities(cities: List<CityModel>): Result<List<CityId>, DataError.Local> {
        return try {
            val entities = cities.map { it.toEntity() }
            citiesDao.upsertCities(entities)
            Result.Success(entities.map { it._id })
        } catch (e: SQLiteFullException) {
            Result.Error(DataError.Local.DISK_FULL)
        }
    }

    override suspend fun deleteCity(id: Int) {
        citiesDao.deleteCity(id)
    }

    override suspend fun deleteAllCities() {
        citiesDao.deleteAllCities()
    }
}