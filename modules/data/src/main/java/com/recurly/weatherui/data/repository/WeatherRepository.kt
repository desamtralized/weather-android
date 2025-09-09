package com.recurly.weatherui.data.repository

import com.recurly.weatherui.data.models.CurrentWeather

/**
 * Repository contract for weather data operations.
 * Abstracts data source implementation from UI layer.
 */
interface WeatherRepository {
    /**
     * Fetches current weather conditions for the default location.
     * @return Result containing weather data or failure with exception
     */
    suspend fun getCurrentWeather(): Result<CurrentWeather>
}