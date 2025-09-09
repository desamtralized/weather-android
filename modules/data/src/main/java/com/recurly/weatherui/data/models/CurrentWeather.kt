package com.recurly.weatherui.data.models

import com.recurly.weatherui.data.utils.TemperatureUnit

/**
 * Domain model representing current weather conditions.
 * @property temperature Current temperature value
 * @property unit Temperature measurement unit (Celsius/Fahrenheit)
 * @property location Human-readable location description
 * @property description Weather condition description
 * @property startTime Forecast period start time
 * @property latitude Geographic latitude coordinate
 * @property longitude Geographic longitude coordinate
 */
data class CurrentWeather(
    val temperature: Int,
    val unit: TemperatureUnit,
    val location: String = "Location Undefined",
    val description: String = "Description Undefined",
    val startTime: String? = null,
    val latitude: Double = 0.0,
    val longitude: Double = 0.0
)