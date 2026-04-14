package com.akshaym.weatherappcompose.di

import com.akshaym.weatherappcompose.feature.home.data.repository.WeatherRepositoryImpl
import com.akshaym.weatherappcompose.feature.home.domain.repository.WeatherRepository
import com.akshaym.weatherappcompose.network.WeatherApi
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepoModule {

    @Binds
    abstract fun bindWeatherRepo(impl: WeatherRepositoryImpl): WeatherRepository
}