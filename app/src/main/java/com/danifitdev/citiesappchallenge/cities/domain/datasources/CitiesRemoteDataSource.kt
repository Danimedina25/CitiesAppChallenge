package com.danifitdev.citiesappchallenge.cities.domain.datasources

import com.danifitdev.citiesappchallenge.cities.domain.model.CityModel
import com.danifitdev.citiesappchallenge.core.domain.util.DataError
import com.danifitdev.citiesappchallenge.core.domain.util.Result

interface CitiesRemoteDataSource {
    suspend fun getCities(): Result<List<CityModel>, DataError.Network>
}