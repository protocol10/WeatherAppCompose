package com.akshaym.weatherappcompose.feature.home.domain.repository

import com.akshaym.weatherappcompose.feature.home.data.CurrentLocationWeatherDataItem
import com.akshaym.weatherappcompose.feature.home.domain.model.CurrentWeatherLocationData
import com.akshaym.weatherappcompose.feature.home.domain.model.WeatherForeCast
import com.akshaym.weatherappcompose.feature.home.domain.model.WeatherLocation

interface WeatherRepository {
    suspend fun getCurrentWeather(lat: Double, long: Double): WeatherLocation

    suspend fun getWeatherForecast(key: String): List<WeatherForeCast>

    suspend fun getCurrentWeatherData(key: String): CurrentWeatherLocationData?
}
