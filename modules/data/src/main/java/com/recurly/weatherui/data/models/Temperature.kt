package com.recurly.weatherui.data.models

import com.recurly.weatherui.data.utils.TemperatureUnit

data class Temperature(
    val value: Int,
    val unit: TemperatureUnit,
    val location: String = "San Jose, CA"
)