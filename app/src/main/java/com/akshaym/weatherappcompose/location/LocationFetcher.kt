package com.akshaym.weatherappcompose.location

import android.annotation.SuppressLint
import android.location.Location
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

interface LocationFetcher {
    suspend fun getCurrentLocation(): Location?
}

class LocationFetcherImpl @Inject constructor(val client: FusedLocationProviderClient) :
    LocationFetcher {
    @SuppressLint("MissingPermission")
    override suspend fun getCurrentLocation(): Location? {
        return try {
            client.getCurrentLocation(
                Priority.PRIORITY_HIGH_ACCURACY, CancellationTokenSource().token
            ).await()
        } catch (e: Exception) {
            null
        }
    }
}
