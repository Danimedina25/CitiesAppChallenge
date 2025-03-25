package com.danifitdev.citiesappchallenge.cities.di

import com.danifitdev.citiesappchallenge.cities.data.local.CitiesLocalDataSourceImpl
import com.danifitdev.citiesappchallenge.cities.data.remote.CitiesRemoteDataSourceImpl
import com.danifitdev.citiesappchallenge.cities.domain.datasources.CitiesLocalDataSource
import com.danifitdev.citiesappchallenge.cities.domain.datasources.CitiesRemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class CitiesDataSourceModule {
    @Binds
    abstract fun bindCitiesRemoteDataSource(
        impl: CitiesRemoteDataSourceImpl
    ): CitiesRemoteDataSource

    @Binds
    abstract fun bindCitiesLocalDataSource(
        impl: CitiesLocalDataSourceImpl
    ): CitiesLocalDataSource
}