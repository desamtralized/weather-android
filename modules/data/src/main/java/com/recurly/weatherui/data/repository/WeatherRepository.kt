package com.recurly.weatherui.data.repository

import com.recurly.weatherui.data.models.Temperature

interface WeatherRepository {
    suspend fun getCurrentTemperature(): Result<Temperature>
}