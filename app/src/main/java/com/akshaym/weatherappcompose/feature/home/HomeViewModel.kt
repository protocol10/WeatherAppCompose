package com.akshaym.weatherappcompose.feature.home

import android.location.Location
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akshaym.weatherappcompose.data.datastore.SaveCurrentLocationStore
import com.akshaym.weatherappcompose.domain.model.SaveLocationModel
import com.akshaym.weatherappcompose.domain.model.WeatherSection
import com.akshaym.weatherappcompose.domain.repository.WeatherRepository
import com.akshaym.weatherappcompose.location.LocationFetcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    val fetcher: LocationFetcher,
    val weatherRepository: WeatherRepository,
    val saveCurrentLocationStore: SaveCurrentLocationStore
) : ViewModel() {

    private val _currentLocation = MutableStateFlow<Location?>(null)
    private val _isInitialLocationLoaded = MutableStateFlow(false)
    private val isInitialLocationLoaded: StateFlow<Boolean> = _isInitialLocationLoaded.asStateFlow()

    private val _list = MutableStateFlow<List<WeatherSection>>(emptyList())

    val uiList: StateFlow<List<WeatherSection>> = _list.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()
    fun getCurrentLocation() {
        Timber.i("getCurrent location")
        if (isInitialLocationLoaded.value || isLoading.value) {
            return
        }

        viewModelScope.launch {

            _isLoading.value = true
            _error.value = null
            try {
                val currentLocation = fetcher.getCurrentLocation()

                Timber.i("Current location calling api $currentLocation")

                currentLocation?.let {
                    val currentWeatherData = async {
                        weatherRepository.getCurrentWeather(
                            currentLocation.latitude, currentLocation.longitude
                        )
                    }.await()
                    val weatherForeCastData =
                        async { weatherRepository.getWeatherForecast(currentWeatherData.locationKey) }.await()

                    val currentLocationWeatherData = async {
                        weatherRepository.getCurrentWeatherData(currentWeatherData.locationKey)
                    }.await()

                    val uiList = mutableListOf<WeatherSection>()

                    uiList.add(
                        WeatherSection.Header(
                            cityName = currentWeatherData.cityName,
                            countryName = currentWeatherData.countryName,
                            temperature = currentLocationWeatherData?.temperature
                        )
                    )
                    uiList.add(WeatherSection.HourlyForeCast(weatherForeCastData))
                    uiList.add(
                        WeatherSection.WeatherMetrics(
                            items = currentLocationWeatherData?.list ?: emptyList()
                        )
                    )
                    saveCurrentLocationStore.saveLocation(
                        SaveLocationModel(
                            locationKey = currentWeatherData.locationKey,
                            cityName = currentWeatherData.cityName,
                            countryName = currentWeatherData.countryName,
                            weatherFeel = "Sunny",
                            lastUpdatedTime = System.currentTimeMillis().toString(),
                            iconType = "3",
                            temperature = "$${currentLocationWeatherData!!.temperature.value} ${currentLocationWeatherData.temperature.unit}"
                        )
                    )
                    _list.value = uiList
                }

                _currentLocation.value = currentLocation
                _isInitialLocationLoaded.value = true
                _isLoading.value = false
            } catch (e: Exception) {
                _isLoading.value = false
                _error.value = e.toString()
                e.printStackTrace()
            }
        }
    }
}