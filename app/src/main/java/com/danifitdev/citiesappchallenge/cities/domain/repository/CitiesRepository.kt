package com.danifitdev.citiesappchallenge.cities.domain.repository

import com.danifitdev.citiesappchallenge.cities.domain.model.CityModel
import com.danifitdev.citiesappchallenge.core.domain.util.DataError
import com.danifitdev.citiesappchallenge.core.domain.util.EmptyResult
import kotlinx.coroutines.flow.Flow


interface CitiesRepository {
    fun getCities(): Flow<List<CityModel>>
    suspend fun getCity(idCity: Int): CityModel?
    suspend fun fetchCities(): EmptyResult<DataError>
    suspend fun deleteAllCities()
    suspend fun toggleFavorite(cityModel: CityModel)
}

