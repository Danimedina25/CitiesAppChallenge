package com.danifitdev.citiesappchallenge.cities.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.danifitdev.citiesappchallenge.R
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import com.danifitdev.citiesappchallenge.cities.domain.model.CityModel
import com.danifitdev.citiesappchallenge.core.presentation.components.CitiesToolbar
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState

@Composable
fun CityMapScreenRoot(
    backStackEntry: NavBackStackEntry?,
    onBackClick: () -> Unit,
    showBackButtonToolbar: Boolean,
){

    val viewModel: CitiesViewModel = if(backStackEntry != null) hiltViewModel(backStackEntry) else hiltViewModel()
    val context = LocalContext.current
    val state by viewModel.state.collectAsStateWithLifecycle()
    val mapStyle = remember {
        MapStyleOptions.loadRawResourceStyle(context, R.raw.map_style)
    }
    val cityLocation = LatLng(state.citySelected.coord.lat, state.citySelected.coord.lon)
    val cameraPositionState = rememberCameraPositionState()
    val markerState = rememberMarkerState(position = cityLocation)

    LaunchedEffect(state.citySelected) {
        cameraPositionState.animate(
            CameraUpdateFactory.newLatLngZoom(cityLocation, 12f)
        )
        markerState.position = cityLocation
    }
    CityMapScreen(
        city = state.citySelected,
        cameraPositionState = cameraPositionState,
        markerState = markerState,
        mapStyle = mapStyle,
        onAction = { action ->
            when(action){
                is CitiesAction.OnBackClick ->{
                    onBackClick()
                }
                else -> Unit
            }
            viewModel.onAction(action)
        },
        showBackButtonToolbar)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CityMapScreen(
    city: CityModel,
    cameraPositionState: CameraPositionState,
    markerState: MarkerState,
    mapStyle: MapStyleOptions,
    onAction: (CitiesAction) -> Unit,
    showBackButtonToolbar: Boolean,
) {
    Scaffold(
        topBar =
        {
            CitiesToolbar(
                showBackButton = showBackButtonToolbar,
                title = stringResource(id = R.string.title_top_bar_city_map, city.name, city.country) ,
                modifier = Modifier,
                onBackClick = {onAction(CitiesAction.OnBackClick)})
        }
        ,
        modifier = Modifier
    ) { padding ->
        Column(modifier = Modifier.padding(padding)){
            GoogleMap(
                cameraPositionState = cameraPositionState,
                properties = MapProperties(
                    mapStyleOptions = mapStyle
                ),
                uiSettings = MapUiSettings(
                    zoomControlsEnabled = true
                ),
                modifier = Modifier
            ) {
                Marker (
                    state = markerState,
                    title = city.name,
                ){

                }
            }
        }

    }
}
