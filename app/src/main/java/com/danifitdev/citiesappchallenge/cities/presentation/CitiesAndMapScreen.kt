package com.danifitdev.citiesappchallenge.cities.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry

@Composable
fun CitiesAndMapScreen(
    onBackClick: () -> Unit,
    backStackEntry: NavBackStackEntry?
) {
    Row(modifier = Modifier.fillMaxSize()) {
        Column(Modifier.weight(1f)){
            CitiesScreenRoot(backStackEntry){}
        }
        Column(Modifier.weight(1f)){
            CityMapScreenRoot(
                backStackEntry = backStackEntry,
                onBackClick = onBackClick,
                showBackButtonToolbar = false,
            )
        }
    }
}
