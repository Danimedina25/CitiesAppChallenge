package com.danifitdev.citiesappchallenge.cities.presentation

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.Immutable
import com.danifitdev.citiesappchallenge.cities.domain.model.CityModel

@Immutable
data class CitiesState(
    val loading: Boolean = false,
    val cities: List<CityModel> = emptyList(),
    val searchQuery: TextFieldState = TextFieldState(),
    val filteredCities: List<CityModel> = emptyList()
)
