package com.akshaym.weatherappcompose.feature.home.domain.model

import java.util.Date

// Weather.kt or LocationInfo.kt  ← Pure clean model, no annotations
data class WeatherLocation(
    val locationKey: String,
    val cityName: String,
    val latitude: Double,
    val longitude: Double,
    val countryName: String
)

data class WeatherForeCast(
    val temperature: Temperature, val dateTime: String, val epochTime: Long, val weatherIcon: Int
)

data class Temperature(val value: Double, val unit: String, val unitType: Int)

data class CurrentWeatherLocationData(
    val temperature: Temperature,
    val weatherText: String,
    val list: List<MetricItem>
)

sealed  class WeatherSection {
    data class Header(val cityName: String, val countryName: String, val temperature: Temperature?) :
        WeatherSection()

    data class HourlyForeCast(val weatherForeCast: List<WeatherForeCast>) : WeatherSection()

    data class WeatherMetrics(val items: List<MetricItem>) : WeatherSection()
}

data class MetricItem(val title: String, val value: String, val icon: Int, val unit: String)