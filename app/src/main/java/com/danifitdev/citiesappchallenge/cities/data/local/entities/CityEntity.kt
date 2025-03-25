package com.danifitdev.citiesappchallenge.cities.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.danifitdev.citiesappchallenge.cities.domain.model.CoordModel

@Entity(tableName = "cities_table")
data class CityEntity(
    @PrimaryKey val _id: Int?,
    val coord: CoordModel,
    val country: String?,
    val name: String?,
    val isFavorite: Boolean
)




