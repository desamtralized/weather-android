package com.recurly.weatherui.data.models

data class Temperature(
    val value: Int,
    val unit: String,
    val location: String = "San Jose, CA"
)