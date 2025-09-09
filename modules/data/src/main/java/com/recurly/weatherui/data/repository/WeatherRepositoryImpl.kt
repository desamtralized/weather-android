package com.recurly.weatherui.data.repository

import com.recurly.weatherui.data.mapper.WeatherDataMapper
import com.recurly.weatherui.data.models.CurrentWeather
import com.recurly.weatherui.data.network.api.WeatherApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRepositoryImpl @Inject constructor(
    private val weatherApi: WeatherApi, private val mapper: WeatherDataMapper
) : WeatherRepository {

    companion object {
        private const val DEFAULT_LAT = 37.2883
        private const val DEFAULT_LON = -121.8434
    }

    override suspend fun getCurrentWeather(): Result<CurrentWeather> {
        return try {
            // Step 1: Get points data
            val pointsResponse = weatherApi.getPoints(DEFAULT_LAT, DEFAULT_LON)

            // Step 2: Get forecast using the forecast URL
            val forecastUrl = pointsResponse.properties.forecast
            val forecastResponse = weatherApi.getForecast(forecastUrl)

            // Step 3: Extract today's temperature
            val todayPeriod = forecastResponse.properties.periods.firstOrNull()
                ?: throw Exception("No forecast data available")
            val relativeLocation = pointsResponse.properties.relativeLocation
            val locationString = relativeLocation?.let {
                "${it.properties.city}, ${it.properties.state}"
            } ?: ""
            val description = todayPeriod.shortForecast
            Result.success(
                mapper.mapToCurrentWeather(todayPeriod, locationString, description)
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}