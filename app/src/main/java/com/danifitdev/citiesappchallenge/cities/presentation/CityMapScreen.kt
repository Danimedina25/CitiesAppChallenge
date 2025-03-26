package com.danifitdev.citiesappchallenge.cities.presentation

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.danifitdev.citiesappchallenge.R
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState import androidx.lifecycle.compose.collectAsStateWithLifecycle
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
    backStackEntry: NavBackStackEntry,
    onBackClick: () -> Unit
){
    val viewModel: CitiesViewModel = hiltViewModel(backStackEntry)
    val context = LocalContext.current
    val state by viewModel.state.collectAsStateWithLifecycle()
    val mapStyle = remember {
        MapStyleOptions.loadRawResourceStyle(context, R.raw.map_style)
    }
    val cameraPositionState = rememberCameraPositionState()
    val markerState = rememberMarkerState(position = LatLng(state.citySelected.coord.lat, state.citySelected.coord.lon))

    LaunchedEffect(state.citySelected.coord.lat, state.citySelected.coord.lon) {
        cameraPositionState.animate(
            CameraUpdateFactory.newLatLngZoom(
                LatLng(state.citySelected.coord.lat, state.citySelected.coord.lon), 12f
            )
        )
    }
    Log.d("prueba",state.searchQuery.text.toString())
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
        } )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CityMapScreen(
    city: CityModel,
    cameraPositionState: CameraPositionState,
    markerState: MarkerState,
    mapStyle: MapStyleOptions,
    onAction: (CitiesAction) -> Unit,
) {
    Scaffold(
        topBar =
        {
            CitiesToolbar(
                showBackButton = true,
                title = stringResource(id = R.string.title_top_bar_city_map) + " de: ${city.name}, ${city.country}",
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
