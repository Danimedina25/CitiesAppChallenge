package com.danifitdev.citiesappchallenge.cities.di

import android.content.Context
import androidx.room.Room
import com.danifitdev.citiesappchallenge.cities.data.local.CitiesAppDatabase
import com.danifitdev.citiesappchallenge.cities.data.local.dao.CitiesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): CitiesAppDatabase {
        return Room.databaseBuilder(
            context,
            CitiesAppDatabase::class.java,
            "cities_database"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideCitiesDao(database: CitiesAppDatabase): CitiesDao {
        return database.citiesDao()
    }
}