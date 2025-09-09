package com.recurly.weatherui.data.mapper

import com.recurly.weatherui.data.models.CurrentWeather
import com.recurly.weatherui.data.models.ForecastPeriod
import com.recurly.weatherui.data.utils.TemperatureUnit
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Maps API response models to domain models.
 * Handles unit conversion and data normalization.
 */
@Singleton
class WeatherDataMapper @Inject constructor() {

    /**
     * Transforms forecast period data into domain weather model.
     * @param period API forecast period response
     * @param location Formatted location string
     * @param description Weather condition description
     * @param latitude Geographic latitude
     * @param longitude Geographic longitude
     * @return Domain weather model with normalized data
     */
    fun mapToCurrentWeather(
        period: ForecastPeriod,
        location: String = "",
        description: String = "",
        latitude: Double,
        longitude: Double
    ): CurrentWeather {
        return CurrentWeather(
            temperature = period.temperature,
            unit = when (period.temperatureUnit.uppercase()) {
                "F" -> TemperatureUnit.FAHRENHEIT
                "C" -> TemperatureUnit.CELSIUS
                else -> TemperatureUnit.FAHRENHEIT // Default to Fahrenheit
            },
            location,
            description,
            startTime = period.startTime,
            latitude = latitude,
            longitude = longitude
        )
    }
}