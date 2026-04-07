package com.akshaym.weatherappcompose.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.akshaym.weatherappcompose.feature.forecast.ForecastScreen
import com.akshaym.weatherappcompose.feature.home.HomeScreen
import com.akshaym.weatherappcompose.feature.location.LocationScreen
import com.akshaym.weatherappcompose.feature.search.SearchScreen

@Composable
fun WeatherNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController, startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) { HomeScreen(navController) }
        composable(Screen.Forecast.route) { ForecastScreen(navController) }
        composable(Screen.Search.route) { SearchScreen(navController) }
        composable(Screen.Locations.route) { LocationScreen(navController) }
    }
}