package com.recurly.weatherui.data.network.api

import com.recurly.weatherui.data.models.ForecastResponse
import com.recurly.weatherui.data.models.PointsResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

interface WeatherApi {
    @GET("points/{lat},{lon}")
    suspend fun getPoints(
        @Path("lat") latitude: Double,
        @Path("lon") longitude: Double
    ): PointsResponse

    @GET
    suspend fun getForecast(@Url forecastUrl: String): ForecastResponse
}