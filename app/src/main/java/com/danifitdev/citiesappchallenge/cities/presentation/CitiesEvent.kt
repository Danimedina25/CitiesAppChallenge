package com.danifitdev.citiesappchallenge.cities.presentation

import com.danifitdev.citiesappchallenge.core.presentation.UiText


sealed interface CitiesEvent {
    data class SearchError(val error: UiText): CitiesEvent
}