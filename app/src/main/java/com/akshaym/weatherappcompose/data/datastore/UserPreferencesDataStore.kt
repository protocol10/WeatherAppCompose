package com.akshaym.weatherappcompose.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.akshaym.weatherappcompose.domain.model.SaveLocationModel
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

class UserPreferencesDataStore @Inject constructor(
    @ApplicationContext private val context: Context, private val gson: Gson
) : SaveCurrentLocationStore {
    private val locationKey = stringPreferencesKey("saved_location_obj")

    override suspend fun saveLocation(location: SaveLocationModel) {
        context.dataStore.edit { preferences ->
            val jsonString = gson.toJson(location)
            preferences[locationKey] = jsonString
        }
    }

    override suspend fun getCurrentLocation(): Flow<SaveLocationModel?> {
        return context.dataStore.data.map { preferences ->
            val jsonString = preferences[locationKey]
            if (jsonString != null) {
                gson.fromJson(jsonString, SaveLocationModel::class.java)
            } else {
                null
            }
        }
    }

    override suspend fun clearSavedLocation() {
        context.dataStore.edit { it.remove(locationKey) }
    }
}

interface SaveCurrentLocationStore {
    suspend fun saveLocation(location: SaveLocationModel)
    suspend fun getCurrentLocation(): Flow<SaveLocationModel?>
    suspend fun clearSavedLocation()
}