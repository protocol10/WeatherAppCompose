package com.akshaym.weatherappcompose.di

import com.akshaym.weatherappcompose.location.LocationFetcher
import com.akshaym.weatherappcompose.location.LocationFetcherImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class LocationModule {

    @Binds
    @Singleton
    abstract fun bindLocationFetcher(
        impl: LocationFetcherImpl
    ): LocationFetcher
}