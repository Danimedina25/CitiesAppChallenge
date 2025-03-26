package com.danifitdev.citiesappchallenge.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.danifitdev.citiesappchallenge.cities.presentation.CitiesScreenRoot
import com.danifitdev.citiesappchallenge.cities.presentation.CityMapScreen
import com.danifitdev.citiesappchallenge.cities.presentation.CityMapScreenRoot

@Composable
fun NavigationRoot(
    navController: NavHostController,
    ) {
    NavHost(
        navController = navController,
        startDestination = Routes.Cities
    ) {
        composable<Routes.Cities> {
            CitiesScreenRoot(
                onCityClick = {navController.navigate(Routes.CityMap)}
            )
        }
        composable<Routes.CityMap>{
            val navBackStackEntry = navController.currentBackStackEntryAsState().value

            val backStackEntry = remember(navBackStackEntry?.destination?.route) {
                navController.getBackStackEntry(Routes.Cities)
            }
            CityMapScreenRoot(backStackEntry = backStackEntry,
                onBackClick = {navController.popBackStack()})
        }
    }
}

