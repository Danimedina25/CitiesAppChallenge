package com.danifitdev.citiesappchallenge.cities.domain.datasources

import com.danifitdev.citiesappchallenge.cities.domain.model.CityModel
import com.danifitdev.citiesappchallenge.core.domain.util.DataError
import kotlinx.coroutines.flow.Flow
import com.danifitdev.citiesappchallenge.core.domain.util.Result

typealias CityId = Int?
interface CitiesLocalDataSource {
    fun getCities(): Flow<List<CityModel>>
    suspend fun upserCity(cityModel: CityModel): Result<CityId, DataError.Local>
    suspend fun upsertCities(cities: List<CityModel>): Result<List<CityId>, DataError.Local>
    suspend fun deleteCity(id: Int)
    suspend fun deleteAllCities()
}