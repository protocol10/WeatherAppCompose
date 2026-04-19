package com.akshaym.weatherappcompose.network

import com.akshaym.weatherappcompose.feature.home.data.CurrentLocationWeatherDataItem
import com.akshaym.weatherappcompose.feature.home.data.ForeCastResponse
import com.akshaym.weatherappcompose.feature.home.data.GeoLocationResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WeatherApi {

    @GET("locations/v1/cities/geoposition/search")
    suspend fun getGeoPosition(@Query("q") query: String): GeoLocationResponse

    @GET("forecasts/v1/hourly/12hour/{locationKey}")
    suspend fun getTwelveHourlyForecast(@Path("locationKey") locationKey: String): List<ForeCastResponse>

    @GET("currentconditions/v1/{locationKey}")
    suspend fun getCurrentLocationWeatherData(
        @Path("locationKey") locationkey: String,
        @Query("details") details: Boolean = true
    ): List<CurrentLocationWeatherDataItem>
}