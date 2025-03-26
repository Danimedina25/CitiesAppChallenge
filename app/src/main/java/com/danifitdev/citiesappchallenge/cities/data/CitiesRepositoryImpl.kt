package com.danifitdev.citiesappchallenge.cities.data

import android.util.Log
import com.danifitdev.citiesappchallenge.cities.domain.datasources.CitiesLocalDataSource
import com.danifitdev.citiesappchallenge.cities.domain.datasources.CitiesRemoteDataSource
import com.danifitdev.citiesappchallenge.cities.domain.model.CityModel
import com.danifitdev.citiesappchallenge.cities.domain.repository.CitiesRepository
import com.danifitdev.citiesappchallenge.core.domain.util.DataError
import com.danifitdev.citiesappchallenge.core.domain.util.EmptyResult
import com.danifitdev.citiesappchallenge.core.domain.util.Result
import com.danifitdev.citiesappchallenge.core.domain.util.asEmptyDataResult
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CitiesRepositoryImpl @Inject constructor(
    private val remoteDataSource: CitiesRemoteDataSource,
    private val localDataSource: CitiesLocalDataSource,
    private val applicationScope: CoroutineScope,
):CitiesRepository {
    override fun getCities(): Flow<List<CityModel>> {
        return localDataSource.getCities()
            .onEach { cities ->
                if (cities.isEmpty()) {
                    fetchCities()
                }
            }
            .flowOn(Dispatchers.IO)
    }

    override suspend fun getCity(idCity: Int): CityModel? {
        return localDataSource.getCity(idCity)
    }

    override suspend fun fetchCities(): EmptyResult<DataError> {
        return when(val result = remoteDataSource.getCities()) {
            is Result.Error -> result.asEmptyDataResult()
            is Result.Success -> {
                applicationScope.async {
                    localDataSource.upsertCities(result.data).asEmptyDataResult()
                }.await()
            }
        }
    }

    override suspend fun deleteAllCities() {
        localDataSource.deleteAllCities()
    }

    override suspend fun toggleFavorite(city: CityModel) {
        localDataSource.upserCity(city)
    }
}