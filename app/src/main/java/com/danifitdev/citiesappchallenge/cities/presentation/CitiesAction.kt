package com.danifitdev.citiesappchallenge.cities.presentation

sealed interface CitiesAction {
    data object OnClickCity: CitiesAction
    data object OnAddFavoriteCity: CitiesAction
    data object OnBackClick: CitiesAction
    data class OnFilterCities(val queryString: String): CitiesAction
    data object OnDismissDialog: CitiesAction
}