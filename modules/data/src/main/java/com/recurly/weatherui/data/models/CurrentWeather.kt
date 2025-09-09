package com.recurly.weatherui.data.models

import com.recurly.weatherui.data.utils.TemperatureUnit

data class CurrentWeather(
    val temperature: Int,
    val unit: TemperatureUnit,
    val location: String = "Location Undefined",
    val description: String = "Description Undefined",
    val startTime: String? = null
)