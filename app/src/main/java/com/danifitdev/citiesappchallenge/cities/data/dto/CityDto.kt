package com.danifitdev.citiesappchallenge.cities.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class CityDto(
    val _id: Int,
    val coord: CoordDto,
    val country: String,
    val name: String
)