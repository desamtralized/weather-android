package com.recurly.weatherui.data.mapper

import com.recurly.weatherui.data.models.ForecastPeriod
import com.recurly.weatherui.data.models.Temperature
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherDataMapper @Inject constructor() {

    fun mapToTemperature(period: ForecastPeriod): Temperature {
        return Temperature(
            value = period.temperature,
            unit = period.temperatureUnit,
            location = "San Jose, CA"
        )
    }
}