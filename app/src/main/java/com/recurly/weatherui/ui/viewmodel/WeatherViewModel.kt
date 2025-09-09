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

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow<TemperatureUiState>(TemperatureUiState.Loading)
    val uiState: StateFlow<TemperatureUiState> = _uiState.asStateFlow()
    
    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()
    
    init {
        loadWeatherData()
    }
    
    fun loadWeatherData() {
        viewModelScope.launch {
            _uiState.value = TemperatureUiState.Loading
            
            weatherRepository.getCurrentTemperature()
                .onSuccess { temperature ->
                    _uiState.value = TemperatureUiState.Success(
                        temperature = temperature.value,
                        unit = temperature.unit,
                        location = temperature.location,
                        lastUpdated = getCurrentTime(),
                        description = "Current Weather"
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
    
    fun refresh() {
        viewModelScope.launch {
            _isRefreshing.value = true
            loadWeatherData()
        }
    }
    
    private fun getCurrentTime(): String {
        val formatter = SimpleDateFormat("h:mm a", Locale.getDefault())
        return formatter.format(Date())
    }
}