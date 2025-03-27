package com.danifitdev.citiesappchallenge.cities.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danifitdev.citiesappchallenge.cities.domain.model.CityModel
import com.danifitdev.citiesappchallenge.cities.domain.repository.CitiesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
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

    val state = _state
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            CitiesState()
        )


    private val eventChannel = Channel<CitiesEvent>()
    val events = eventChannel.receiveAsFlow()

    init {
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
            is CitiesAction.OnShowCityInfo -> {
                _state.update {
                    it.copy(
                        citySelected = action.city,
                        showCityInfo = true
                    )
                }
            }
            CitiesAction.OnDismissDialog -> {
                _state.update {
                    it.copy(
                        showCityInfo = false
                    )
                }
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

    fun getCities() {
        _state.update { it.copy(loading = true)}
        repository.getCities()
            .onEach { cities ->
                if (cities.isNotEmpty()) {
                    setCities(cities)
                }
                _state.update { it.copy(loading = false) }
            }
            .catch { e ->
                _state.update { it.copy(loading = false) }
            }
            .launchIn(viewModelScope)
    }

    private fun deleteAllCities(){
        viewModelScope.launch {
            repository.deleteAllCities()
        }
    }

    fun setNewSearchQuery(queryString: String){
        _state.value.searchQuery.edit { this.replace(0, this.length, queryString) }
    }

    fun filterCities() {
        val cities = _state.value.cities
        val searchQuery = _state.value.searchQuery.text
        viewModelScope.launch {
            val filteredList = cities.filter { city ->
                normalizeCityName(city.name).startsWith(searchQuery, ignoreCase = true)
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

    fun setCities(cities: List<CityModel>) {
        viewModelScope.launch {
            _state.update { it.copy(cities = cities.sortedBy { it.name }) }
        }
    }
}