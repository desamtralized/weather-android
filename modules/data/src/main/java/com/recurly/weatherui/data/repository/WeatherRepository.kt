package com.recurly.weatherui.data.repository

import com.recurly.weatherui.data.models.CurrentWeather

interface WeatherRepository {
    suspend fun getCurrentWeather(): Result<CurrentWeather>
}