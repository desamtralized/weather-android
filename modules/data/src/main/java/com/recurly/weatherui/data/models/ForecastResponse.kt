package com.recurly.weatherui.data.models

import com.google.gson.annotations.SerializedName

data class ForecastResponse(
    @SerializedName("properties")
    val properties: ForecastProperties
)

data class ForecastProperties(
    @SerializedName("periods")
    val periods: List<ForecastPeriod>
)

data class ForecastPeriod(
    @SerializedName("number")
    val number: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("temperature")
    val temperature: Int,
    @SerializedName("temperatureUnit")
    val temperatureUnit: String,
    @SerializedName("windSpeed")
    val windSpeed: String,
    @SerializedName("windDirection")
    val windDirection: String,
    @SerializedName("icon")
    val icon: String,
    @SerializedName("shortForecast")
    val shortForecast: String,
    @SerializedName("detailedForecast")
    val detailedForecast: String
)