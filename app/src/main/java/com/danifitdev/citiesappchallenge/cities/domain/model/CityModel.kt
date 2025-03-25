package com.danifitdev.citiesappchallenge.cities.domain.model

data class CityModel(
    val _id: Int,
    val coord: CoordModel,
    val country: String,
    val name: String,
    val isFavorite: Boolean
)