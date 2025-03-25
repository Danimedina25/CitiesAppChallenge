package com.danifitdev.citiesappchallenge.core.navigation

import kotlinx.serialization.Serializable

sealed class Routes {

    @Serializable
    data object Cities: Routes()

    @Serializable
    data object CityDetail: Routes()

    @Serializable
    data object CityMap : Routes()

}