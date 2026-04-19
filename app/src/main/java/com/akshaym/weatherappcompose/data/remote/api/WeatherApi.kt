package com.akshaym.weatherappcompose.data.remote.api

import com.akshaym.weatherappcompose.data.remote.model.CurrentLocationWeatherDataItem
import com.akshaym.weatherappcompose.data.remote.model.ForeCastResponse
import com.akshaym.weatherappcompose.data.remote.model.GeoLocationResponse
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