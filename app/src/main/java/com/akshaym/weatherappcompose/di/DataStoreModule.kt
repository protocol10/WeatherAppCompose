package com.akshaym.weatherappcompose.di

import com.akshaym.weatherappcompose.data.datastore.SaveCurrentLocationStore
import com.akshaym.weatherappcompose.data.datastore.UserPreferencesDataStore
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataStoreModule {

    @Binds
    @Singleton
    abstract fun bindSaveCurrentLocationStore(
        userPreferencesDataStore: UserPreferencesDataStore
    ): SaveCurrentLocationStore
}
