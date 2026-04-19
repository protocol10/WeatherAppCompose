package com.akshaym.weatherappcompose.domain.repository

import com.akshaym.weatherappcompose.domain.model.CurrentWeatherLocationData
import com.akshaym.weatherappcompose.domain.model.WeatherForeCast
import com.akshaym.weatherappcompose.domain.model.WeatherLocation

interface WeatherRepository {
    suspend fun getCurrentWeather(lat: Double, long: Double): WeatherLocation

    suspend fun getWeatherForecast(key: String): List<WeatherForeCast>

    suspend fun getCurrentWeatherData(key: String): CurrentWeatherLocationData?
}
