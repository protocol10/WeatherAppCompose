package com.akshaym.weatherappcompose.feature.home.data

import com.google.gson.annotations.SerializedName
import java.sql.Date

data class GeoLocationResponse(
    @SerializedName("EnglishName") val englishName: String,
    @SerializedName("Key") val key: String,
    @SerializedName("LocalizedName") val localizedName: String,
    @SerializedName("PrimaryPostalCode") val primaryPostalCode: String,
    @SerializedName("GeoPosition") val geoLocationResponse: GeoPositionResponse,
    @SerializedName("Country") val countryResponse: CountryResponse
)

data class CountryResponse(
    @SerializedName("ID") val id: String,
    @SerializedName("LocalizedName") val localizedName: String,
    @SerializedName("EnglishName") val englishName: String
)

data class GeoPositionResponse(
    @SerializedName("Latitude") val latitude: Double,
    @SerializedName("Longitude") val longitude: Double,
)

data class ForeCastResponse(
    @SerializedName("DateTime") val dateTime: String,
    @SerializedName("EpochDateTime") val epochDateTime: Long,
    @SerializedName("HasPrecipitation") val hasPrecipitation: Boolean,
    @SerializedName("IconPhrase") val iconPhrase: String,
    @SerializedName("IsDaylight") val isDaylight: Boolean,
    @SerializedName("Temperature") val temperature: Temperature,
    @SerializedName("WeatherIcon") val weatherIcon: Int
)

data class Temperature(
    @SerializedName("Unit") val unit: String,
    @SerializedName("UnitType") val unitType: Int,
    @SerializedName("Value") val value: Double
)

data class CurrentLocationWeatherDataItem(
    @SerializedName("EpochTime") val epochTime: Int,
    @SerializedName("IsDayTime") val isDayTime: Boolean,
    @SerializedName("LocalObservationDateTime") val localObservationDateTime: String,
    @SerializedName("Temperature") val temperature: CurrentLocationTemperature,
    @SerializedName("WeatherIcon") val weatherIcon: Int,
    @SerializedName("WeatherText") val weatherText: String,
    @SerializedName("RealFeelTemperature") val realFeelTemperature: RealFeelTemperature,
    @SerializedName("RelativeHumidity") val relativeHumidity: Int,
    @SerializedName("Wind") val wind: WindResponse,
    @SerializedName("Visibility") val visibility: Visibility,
    @SerializedName("Pressure") val pressure: Pressure,
    @SerializedName("UVIndex") val uvIndex: Int,
)

data class Visibility(
    @SerializedName("Imperial") val imperial: Metric,
    @SerializedName("Metric") val metric: Metric
)

data class Pressure(
    @SerializedName("Imperial") val imperial: Metric,
    @SerializedName("Metric") val metric: Metric
)

data class WindResponse(
    @SerializedName("Speed") val speed: Speed,
    @SerializedName("Direction") val direction: WindDirection,
)

data class Speed(
    @SerializedName("Imperial") val imperial: Metric,
    @SerializedName("Metric") val metric: Metric
)

data class RealFeelTemperature(
    @SerializedName("Imperial") val imperial: Metric,
    @SerializedName("Metric") val metric: Metric
)

data class CurrentLocationTemperature(
    @SerializedName("Imperial") val imperial: Metric, @SerializedName("Metric") val metric: Metric
)

data class Metric(
    @SerializedName("Unit") val unit: String,
    @SerializedName("UnitType") val unitType: Int,
    @SerializedName("Value") val value: Double
)

data class WindDirection(
    @SerializedName("Degrees") val degrees: Int,
    @SerializedName("Localized") val localized: String,
    @SerializedName("English") val english: String
)