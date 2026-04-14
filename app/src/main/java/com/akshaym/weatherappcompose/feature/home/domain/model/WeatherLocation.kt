package com.akshaym.weatherappcompose.feature.home.domain.model

import java.util.Date

// Weather.kt or LocationInfo.kt  ← Pure clean model, no annotations
data class WeatherLocation(
    val locationKey: String, val cityName: String, val latitude: Double, val longitude: Double
)

data class WeatherForeCast(
    val temperature: Temperature, val dateTime: String, val epochTime: Long, val weatherIcon: Int
)

data class Temperature(val value: Double, val unit: String, val unitType: Int)