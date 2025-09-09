package com.recurly.weatherui.uiwidget.state

import com.recurly.weatherui.data.utils.TemperatureUnit

/**
 * UI state representation for temperature widget.
 * Encapsulates all possible states during weather data lifecycle.
 */
sealed interface TemperatureUiState {
    /** Initial loading state before data fetch */
    data object Loading : TemperatureUiState
    
    /**
     * Successful weather data retrieval state.
     * @property temperature Current temperature value
     * @property unit Temperature measurement unit
     * @property location Formatted location string
     * @property lastUpdated Timestamp of last data refresh
     * @property description Weather condition description
     * @property startTime Forecast period start for day/night detection
     * @property latitude Geographic latitude for map display
     * @property longitude Geographic longitude for map display
     */
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
    
    /**
     * Error state with retry capability.
     * @property message User-friendly error description
     * @property canRetry Whether retry action is available
     */
    data class Error(
        val message: String,
        val canRetry: Boolean = true
    ) : TemperatureUiState
}