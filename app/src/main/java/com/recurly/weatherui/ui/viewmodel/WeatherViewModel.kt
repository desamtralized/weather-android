package com.recurly.weatherui.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.recurly.weatherui.data.repository.WeatherRepository
import com.recurly.weatherui.uiwidget.state.TemperatureUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

/**
 * Manages weather data fetching and UI state coordination.
 * Handles automatic refresh on initialization and manual refresh.
 */
@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<TemperatureUiState>(TemperatureUiState.Loading)
    /** Observable weather UI state for Compose consumption */
    val uiState: StateFlow<TemperatureUiState> = _uiState.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    /** Refresh indicator state */
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

    init {
        loadWeatherData()
    }

    /** Fetches current weather from repository and updates UI state */
    fun loadWeatherData() {
        viewModelScope.launch {
            _uiState.value = TemperatureUiState.Loading

            weatherRepository.getCurrentWeather()
                .onSuccess { currentWeather ->
                    _uiState.value = TemperatureUiState.Success(
                        temperature = currentWeather.temperature,
                        unit = currentWeather.unit,
                        location = currentWeather.location,
                        lastUpdated = getCurrentTime(),
                        description = currentWeather.description,
                        startTime = currentWeather.startTime,
                        latitude = currentWeather.latitude,
                        longitude = currentWeather.longitude
                    )
                }
                .onFailure { exception ->
                    _uiState.value = TemperatureUiState.Error(
                        message = exception.message ?: "Failed to load weather data",
                        canRetry = true
                    )
                }

            _isRefreshing.value = false
        }
    }

    /** Triggers manual refresh with loading indicator */
    fun refresh() {
        viewModelScope.launch {
            _isRefreshing.value = true
            loadWeatherData()
        }
    }

    /** Formats current time for "last updated" display */
    private fun getCurrentTime(): String {
        val formatter = SimpleDateFormat("h:mm a", Locale.getDefault())
        return formatter.format(Date())
    }
}