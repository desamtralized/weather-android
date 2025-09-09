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
    val forecastGridData: String,
    @SerializedName("relativeLocation")
    val relativeLocation: RelativeLocation? = null
)

data class RelativeLocation(
    @SerializedName("type")
    val type: String,
    @SerializedName("geometry")
    val geometry: RelativeLocationGeometry,
    @SerializedName("properties")
    val properties: RelativeLocationProperties
)

data class RelativeLocationGeometry(
    @SerializedName("type")
    val type: String,
    @SerializedName("coordinates")
    val coordinates: List<Double>
)

data class RelativeLocationProperties(
    @SerializedName("city")
    val city: String,
    @SerializedName("state")
    val state: String,
    @SerializedName("distance")
    val distance: QuantitativeValue,
    @SerializedName("bearing")
    val bearing: QuantitativeValue
)

data class QuantitativeValue(
    @SerializedName("unitCode")
    val unitCode: String,
    @SerializedName("value")
    val value: Double
)