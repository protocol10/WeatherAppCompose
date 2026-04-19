package com.akshaym.weatherappcompose.feature.home.data.repository

import com.akshaym.weatherappcompose.feature.home.data.CurrentLocationWeatherDataItem
import com.akshaym.weatherappcompose.feature.home.data.ForeCastResponse
import com.akshaym.weatherappcompose.feature.home.data.GeoLocationResponse
import com.akshaym.weatherappcompose.feature.home.domain.model.CurrentWeatherLocationData
import com.akshaym.weatherappcompose.feature.home.domain.model.MetricItem
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

    override suspend fun getCurrentWeatherData(key: String): CurrentWeatherLocationData? {
        val response = weatherApi.getCurrentLocationWeatherData(key)
        return response.toCurrentWeatherData()
    }
}

private fun List<CurrentLocationWeatherDataItem>.toCurrentWeatherData(): CurrentWeatherLocationData? {
    if (!this.isEmpty()) {
        with(this[0]) {
            val metricItem = mutableListOf<MetricItem>()
            metricItem.add(MetricItem("Humidity", relativeHumidity.toString(), 0, "%"))
            metricItem.add(MetricItem("WindSpeed", wind.speed.metric.value.toString(), 0, "mph"))
            metricItem.add(MetricItem("Visibility", visibility.metric.value.toString(), 0, "mi"))
            metricItem.add(MetricItem("Pressure", pressure.metric.value.toString(), 0, "in"))
            metricItem.add(MetricItem("UVIndex", uvIndex.toString(), 0, ""))
            metricItem.add(
                MetricItem(
                    "Feels Like",
                    realFeelTemperature.metric.value.toString(),
                    0,
                    ""
                )
            )
            return CurrentWeatherLocationData(
                temperature = Temperature(
                    value = this.temperature.metric.value,
                    unit = this.temperature.metric.unit,
                    unitType = this.temperature.metric.unitType
                ), weatherText = this.weatherText,
                list = metricItem
            )
        }

    }
    return null
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
                ), dateTime = i.dateTime, epochTime = i.epochDateTime, weatherIcon = i.weatherIcon
            )
        )
    }
    return l
}

private fun GeoLocationResponse.toWeatherLocation(): WeatherLocation {
    return WeatherLocation(
        locationKey = key,
        cityName = englishName,
        latitude = geoLocationResponse.latitude,
        longitude = geoLocationResponse.longitude,
        countryName = countryResponse.localizedName
    )
}
