package com.recurly.weatherui.data.mapper

import com.recurly.weatherui.data.models.CurrentWeather
import com.recurly.weatherui.data.models.ForecastPeriod
import com.recurly.weatherui.data.utils.TemperatureUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherDataMapper @Inject constructor() {

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