package com.danifitdev.citiesappchallenge.cities.data.remote


import com.danifitdev.citiesappchallenge.cities.data.mappers.toDomain
import com.danifitdev.citiesappchallenge.core.domain.util.DataError
import com.danifitdev.citiesappchallenge.cities.domain.datasources.CitiesRemoteDataSource
import com.danifitdev.citiesappchallenge.cities.domain.model.CityModel
import com.danifitdev.citiesappchallenge.core.domain.util.Result
import javax.inject.Inject


class CitiesRemoteDataSourceImpl @Inject constructor(private val apiService: CitiesApiService): CitiesRemoteDataSource {
    override suspend fun getCities(): Result<List<CityModel>, DataError.Network> {
        return try {
            val response = apiService.getCities()
            Result.Success(response.map { it.toDomain() })
        } catch (e: Exception) {
            val error = when (e) {
                is java.net.UnknownHostException -> DataError.Network.NO_INTERNET
                is java.net.SocketTimeoutException -> DataError.Network.REQUEST_TIMEOUT
                else -> DataError.Network.UNKNOWN
            }
            Result.Error(error)
        }
    }
}