package com.recurly.weatherui.data.mapper

import com.recurly.weatherui.data.models.ForecastPeriod
import com.recurly.weatherui.data.models.Temperature
import com.recurly.weatherui.data.utils.TemperatureUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherDataMapper @Inject constructor() {

    fun mapToTemperature(period: ForecastPeriod): Temperature {
        return Temperature(
            value = period.temperature,
            unit = when (period.temperatureUnit.uppercase()) {
                "F" -> TemperatureUnit.FAHRENHEIT
                "C" -> TemperatureUnit.CELSIUS
                else -> TemperatureUnit.FAHRENHEIT // Default to Fahrenheit
            },
            location = "San Jose, CA"
        )
    }
}