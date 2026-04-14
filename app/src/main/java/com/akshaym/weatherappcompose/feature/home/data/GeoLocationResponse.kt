package com.akshaym.weatherappcompose.feature.home.data

import com.google.gson.annotations.SerializedName
import java.sql.Date

data class GeoLocationResponse(
    @SerializedName("EnglishName") val englishName: String,
    @SerializedName("Key") val key: String,
    @SerializedName("LocalizedName") val localizedName: String,
    @SerializedName("PrimaryPostalCode") val primaryPostalCode: String,
    @SerializedName("GeoPosition") val geoLocationResponse: GeoPositionResponse
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