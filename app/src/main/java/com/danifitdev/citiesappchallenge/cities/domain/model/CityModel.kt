package com.danifitdev.citiesappchallenge.cities.domain.model

data class CityModel(
    val _id: Int = 0,
    val coord: CoordModel = CoordModel(0.0, 0.0),
    val country: String = "",
    val name: String = "",
    val isFavorite: Boolean = false
)