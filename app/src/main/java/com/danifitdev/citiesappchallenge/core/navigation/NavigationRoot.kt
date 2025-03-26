package com.danifitdev.citiesappchallenge.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.danifitdev.citiesappchallenge.cities.presentation.CitiesAndMapScreen
import com.danifitdev.citiesappchallenge.cities.presentation.CitiesScreenRoot
import com.danifitdev.citiesappchallenge.cities.presentation.CityMapScreenRoot
import com.danifitdev.citiesappchallenge.core.presentation.isLandscape

@Composable
fun NavigationRoot(
    navController: NavHostController,
    ) {
    val landscape = isLandscape()
    NavHost(
        navController = navController,
        startDestination = Routes.Cities
    ) {
        composable<Routes.Cities> {
            if(!landscape){
                CitiesScreenRoot(
                    onCityClick = { navController.navigate(Routes.CityMap) },
                    backStackEntry = null
                )
            }else{
                CitiesAndMapScreen(backStackEntry = null,
                    onBackClick = { }
                )
            }
        }
        composable<Routes.CityMap> {
            val navBackStackEntry = navController.currentBackStackEntryAsState().value

            val backStackEntry = remember(navBackStackEntry?.destination?.route) {
                navController.getBackStackEntry(Routes.Cities)
            }
            if(!landscape){
                CityMapScreenRoot(backStackEntry = backStackEntry,
                    onBackClick = { navController.popBackStack() },
                    showBackButtonToolbar = true)
            }else{
                CitiesAndMapScreen(backStackEntry = backStackEntry,
                    onBackClick = { }
                )
            }
        }
    }
}

