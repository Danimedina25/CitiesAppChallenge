package com.danifitdev.citiesappchallenge.cities.di

import com.danifitdev.citiesappchallenge.cities.data.CitiesRepositoryImpl
import com.danifitdev.citiesappchallenge.cities.domain.repository.CitiesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class CitiesRepositoryModule {

    @Binds
    abstract fun bindCitiesRepository(
        impl: CitiesRepositoryImpl
    ): CitiesRepository
}