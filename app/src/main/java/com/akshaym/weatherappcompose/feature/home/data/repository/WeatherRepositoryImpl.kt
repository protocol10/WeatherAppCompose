package com.akshaym.weatherappcompose.feature.home.data.repository

import com.akshaym.weatherappcompose.feature.home.data.ForeCastResponse
import com.akshaym.weatherappcompose.feature.home.data.GeoLocationResponse
import com.akshaym.weatherappcompose.feature.home.domain.model.Temperature
import com.akshaym.weatherappcompose.feature.home.domain.model.WeatherForeCast
import com.akshaym.weatherappcompose.feature.home.domain.model.WeatherLocation
import com.akshaym.weatherappcompose.feature.home.domain.repository.WeatherRepository
import com.akshaym.weatherappcompose.network.WeatherApi
import timber.log.Timber
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(val weatherApi: WeatherApi) : WeatherRepository {
    override suspend fun getCurrentWeather(
        lat: Double, long: Double
    ): WeatherLocation {
        val response = weatherApi.getGeoPosition("$lat,$long")
        return response.toWeatherLocation()
    }

    override suspend fun getWeatherForecast(key: String): List<WeatherForeCast> {
        val response = weatherApi.getTwelveHourlyForecast(key)
        return response.toForeCast()
    }
}

private fun List<ForeCastResponse>.toForeCast(): List<WeatherForeCast> {
    val l = mutableListOf<WeatherForeCast>()
    for (i in this) {
        l.add(
            WeatherForeCast(
                temperature = Temperature(
                    value = i.temperature.value,
                    unit = i.temperature.unit,
                    unitType = i.temperature.unitType
                ),
                dateTime = i.dateTime,
                epochTime = i.epochDateTime,
                weatherIcon = i.weatherIcon
            )
        )
    }
    return l
}

private fun GeoLocationResponse.toWeatherLocation(): WeatherLocation {
    Timber.i("to weather location $this")
    return WeatherLocation(
        locationKey = key,
        cityName = englishName,
        latitude = geoLocationResponse.latitude,
        longitude = geoLocationResponse.longitude,
    )
}
