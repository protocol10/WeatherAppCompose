package com.akshaym.weatherappcompose.feature.home

import android.location.Location
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akshaym.weatherappcompose.location.LocationFetcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(val fetcher: LocationFetcher) : ViewModel() {

    private val _currentLocation = MutableStateFlow<Location?>(null)
    val currentLocation: StateFlow<Location?> = _currentLocation.asStateFlow()
    private val _isInitialLocationLoaded = MutableStateFlow(false)
    private val isInitialLocationLoaded: StateFlow<Boolean> = _isInitialLocationLoaded.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()
    fun getCurrentLocation() {
        Timber.i("getCurrent location")
        if (isInitialLocationLoaded.value) {
            return
        }

        viewModelScope.launch {

            _isLoading.value = true
            _error.value = null
            try {
                val currentLocation = fetcher.getCurrentLocation()
                _currentLocation.value = currentLocation
                _isInitialLocationLoaded.value = true
                _isLoading.value = false
            } catch (e: Exception) {
                _isLoading.value = false
                _error.value = e.toString()
            }
        }
    }
}