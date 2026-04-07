package com.akshaym.weatherappcompose.di

import com.akshaym.weatherappcompose.network.WeatherApi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


const val WEATHER_BASE_URL = "https://dataservice.accuweather.com/"

@Module
object NetworkModule {
    @Provides
    fun providesOkHttp(): OkHttpClient {
        return OkHttpClient.Builder().build()
    }

    @Provides
    fun providesRetrofit(okHttpClient: OkHttpClient) {
        Retrofit.Builder().baseUrl(WEATHER_BASE_URL).client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(WeatherApi::class.java)
    }
}