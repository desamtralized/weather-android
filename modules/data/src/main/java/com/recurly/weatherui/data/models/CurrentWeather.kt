package com.recurly.weatherui.data.models

import com.recurly.weatherui.data.utils.TemperatureUnit

data class CurrentWeather(
    val temperature: Int,
    val unit: TemperatureUnit,
    val location: String = "Location Undefined",
    val description: String = "Description Undefined",
    val startTime: String? = null,
    val latitude: Double = 0.0,
    val longitude: Double = 0.0
)