package com.akshaym.weatherappcompose.ui.navigation

sealed class Screen(val route: String) {
    data object Home : Screen("Home")
    data object Forecast : Screen("Forecast")
    data object Locations : Screen("Locations")
    data object Search : Screen("Search")
}