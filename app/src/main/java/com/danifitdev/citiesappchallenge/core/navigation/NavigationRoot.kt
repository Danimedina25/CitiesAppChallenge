package com.danifitdev.citiesappchallenge.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.danifitdev.citiesappchallenge.cities.presentation.CitiesScreenRoot

@Composable
fun NavigationRoot(
    navController: NavHostController,
    ) {
    NavHost(
        navController = navController,
        startDestination = Routes.Cities
    ) {
        composable<Routes.Cities> {
            CitiesScreenRoot()
        }
        composable<Routes.CityMap> {
           /* BenevitsScreenRoot(
               onLogoutSuccess =  {
                    navController.navigate(Routes.Login){
                        popUpTo(Routes.Login) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )*/
        }
    }
}

