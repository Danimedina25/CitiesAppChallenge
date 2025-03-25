package com.danifitdev.citiesappchallenge.cities.di

import com.danifitdev.citiesappchallenge.cities.data.remote.CitiesApiService
import com.danifitdev.citiesappchallenge.core.data.networking.RetrofitProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object CitiesNetworkModule {

    @Provides
    fun provideCitiesApiService(retrofitProvider: RetrofitProvider): CitiesApiService {
        return retrofitProvider.provideApiService(CitiesApiService::class.java)
    }

    /* @Provides
 fun provideCitiesRemoteDataSource(
     apiService: CitiesApiService
 ): CitiesRemoteDataSource {
     return CitiesRemoteDataSourceImpl(apiService)
 }

 @Provides
 fun provideCitiesLocalDataSource(
     citiesDao: CitiesDao
 ): CitiesLocalDataSource {
     return CitiesLocalDataSourceImpl(citiesDao)
 }*/
}