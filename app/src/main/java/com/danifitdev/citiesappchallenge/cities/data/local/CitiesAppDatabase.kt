package com.danifitdev.citiesappchallenge.cities.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.danifitdev.citiesappchallenge.cities.data.local.converters.Converters
import com.danifitdev.citiesappchallenge.cities.data.local.dao.CitiesDao
import com.danifitdev.citiesappchallenge.cities.data.local.entities.CityEntity

@Database(entities = [CityEntity::class], version = 2)
@TypeConverters(Converters::class)
abstract class CitiesAppDatabase : RoomDatabase() {
    abstract fun citiesDao(): CitiesDao
}