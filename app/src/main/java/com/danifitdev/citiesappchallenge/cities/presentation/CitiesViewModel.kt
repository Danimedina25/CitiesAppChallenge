package com.danifitdev.citiesappchallenge.cities.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danifitdev.citiesappchallenge.R
import com.danifitdev.citiesappchallenge.cities.domain.model.CityModel
import com.danifitdev.citiesappchallenge.cities.domain.repository.CitiesRepository
import com.danifitdev.citiesappchallenge.core.presentation.UiText
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CitiesViewModel @Inject constructor(
    private val repository: CitiesRepository
) : ViewModel() {

    private val _state = MutableStateFlow(CitiesState())
    val state: StateFlow<CitiesState> = _state

    private val eventChannel = Channel<CitiesEvent>()
    val events = eventChannel.receiveAsFlow()

    init {
        //deleteAllCities()
        getCities()
    }

    fun onAction(action: CitiesAction) {
        when(action) {
            is CitiesAction.OnFilterCities -> {
                setNewSearchQuery(action.queryString)
                filterCities()
            }
            is CitiesAction.OnAddFavoriteCity -> {
                toggleFavorite(action.city)
            }
            CitiesAction.OnBackClick -> {

            }
            is CitiesAction.OnFilterFavorites -> {
                showFavorites(action.showFavorites)
            }
            else -> Unit
        }
    }

    fun setSelectedCity(city: CityModel){
        _state.update { it.copy(citySelected = city) }
    }

    private fun getCities() {
        _state.update { it.copy(loading = true)}
        repository.getCities()
            .onEach { cities ->
            if(cities.isNotEmpty())
                _state.update { it.copy(cities = cities.sortedBy { it.name }, loading = false)}
        }.launchIn(viewModelScope)
    }

    private fun deleteAllCities(){
        viewModelScope.launch {
            repository.deleteAllCities()
        }
    }

    private fun setNewSearchQuery(queryString: String){
        _state.value.searchQuery.edit { this.replace(0, this.length, queryString) }
    }

    private fun filterCities() {
        viewModelScope.launch {
            val filteredList = state.value.cities.filter { city ->
                normalizeCityName(city.name).startsWith(_state.value.searchQuery.text.toString(), ignoreCase = true)
            }.sortedBy { it.name }

            _state.update {
                it.copy(
                    filteredCities = filteredList,
                )
            }
        }
    }

    private fun showFavorites(showFavorites: Boolean) {
        _state.update {
            it.copy(showFavoritesOnly = showFavorites)
        }
    }

    fun normalizeCityName(city: String): String {
        return city.lowercase()
    }

    fun toggleFavorite(city: CityModel) {
        val toggledFavoriteCity = city.copy(isFavorite = !city.isFavorite)
        viewModelScope.launch {
            repository.toggleFavorite(toggledFavoriteCity)
        }
    }
}