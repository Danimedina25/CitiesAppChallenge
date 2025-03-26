package com.danifitdev.citiesappchallenge.cities.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.danifitdev.citiesappchallenge.cities.data.local.entities.CityEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CitiesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCities(cities: List<CityEntity>): List<Long>

    @Upsert
    suspend fun upsertCities(cities: List<CityEntity>)

    @Upsert
    suspend fun upsertCity(city: CityEntity)

    @Query("SELECT * FROM cities_table")
    fun getCities(): Flow<List<CityEntity>>

    @Query("SELECT * FROM cities_table WHERE _id = :idCity LIMIT 1")
    fun getCity(idCity: Int?): CityEntity?

    @Query("DELETE FROM cities_table WHERE _id = :idCity")
    suspend fun deleteCity(idCity: Int)

    @Query("DELETE FROM cities_table")
    suspend fun deleteAllCities()
}