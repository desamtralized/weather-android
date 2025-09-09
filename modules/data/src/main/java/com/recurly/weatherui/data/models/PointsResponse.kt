package com.recurly.weatherui.data.models

import com.google.gson.annotations.SerializedName

data class PointsResponse(
    @SerializedName("properties")
    val properties: PointsProperties
)

data class PointsProperties(
    @SerializedName("forecast")
    val forecast: String,
    @SerializedName("forecastHourly")
    val forecastHourly: String,
    @SerializedName("forecastGridData")
    val forecastGridData: String
)