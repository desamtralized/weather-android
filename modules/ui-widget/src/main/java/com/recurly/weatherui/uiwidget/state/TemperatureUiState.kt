package com.recurly.weatherui.uiwidget.state

import com.recurly.weatherui.data.utils.TemperatureUnit

sealed interface TemperatureUiState {
    data object Loading : TemperatureUiState
    
    data class Success(
        val temperature: Int,
        val unit: TemperatureUnit,
        val location: String,
        val lastUpdated: String,
        val description: String? = null,
        val startTime: String? = null,
        val latitude: Double = 0.0,
        val longitude: Double = 0.0
    ) : TemperatureUiState
    
    data class Error(
        val message: String,
        val canRetry: Boolean = true
    ) : TemperatureUiState
}