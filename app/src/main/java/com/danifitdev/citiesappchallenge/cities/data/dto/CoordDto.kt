package com.danifitdev.citiesappchallenge.cities.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class CoordDto(
    val lat: Double,
    val lon: Double
)