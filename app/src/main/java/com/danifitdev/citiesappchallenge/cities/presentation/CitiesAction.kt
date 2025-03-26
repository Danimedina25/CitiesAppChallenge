package com.danifitdev.citiesappchallenge.cities.presentation

import com.danifitdev.citiesappchallenge.cities.domain.model.CityModel

sealed interface CitiesAction {
    data class OnClickCity(val city: CityModel): CitiesAction
    data object OnAddFavoriteCity: CitiesAction
    data object OnBackClick: CitiesAction
    data class OnFilterCities(val queryString: String): CitiesAction
    data object OnDismissDialog: CitiesAction
}