package com.recurly.weatherui.data

import com.recurly.weatherui.data.mapper.WeatherDataMapper
import com.recurly.weatherui.data.models.CurrentWeather
import com.recurly.weatherui.data.models.ForecastPeriod
import com.recurly.weatherui.data.models.ForecastProperties
import com.recurly.weatherui.data.models.ForecastResponse
import com.recurly.weatherui.data.models.PointsProperties
import com.recurly.weatherui.data.models.PointsResponse
import com.recurly.weatherui.data.models.QuantitativeValue
import com.recurly.weatherui.data.models.RelativeLocation
import com.recurly.weatherui.data.models.RelativeLocationGeometry
import com.recurly.weatherui.data.models.RelativeLocationProperties
import com.recurly.weatherui.data.network.api.WeatherApi
import com.recurly.weatherui.data.repository.WeatherRepository
import com.recurly.weatherui.data.repository.WeatherRepositoryImpl
import com.recurly.weatherui.data.utils.TemperatureUnit
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class WeatherRepositoryTest {

    @MockK
    private lateinit var weatherApi: WeatherApi

    @MockK
    private lateinit var mapper: WeatherDataMapper

    private lateinit var repository: WeatherRepository

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        repository = WeatherRepositoryImpl(weatherApi, mapper)
    }

    @Test
    fun `getCurrentTemperature returns success with valid data`() = runTest {
        // Given
        val mockRelativeLocation = RelativeLocation(
            type = "Feature",
            geometry = RelativeLocationGeometry(
                type = "Point",
                coordinates = listOf(-121.8434, 37.2883)
            ),
            properties = RelativeLocationProperties(
                city = "San Jose",
                state = "CA",
                distance = QuantitativeValue(unitCode = "wmoUnit:m", value = 0.0),
                bearing = QuantitativeValue(unitCode = "wmoUnit:degree_(angle)", value = 0.0)
            )
        )
        val mockPointsResponse = PointsResponse(
            properties = PointsProperties(
                forecast = "https://api.weather.gov/forecast/1",
                forecastHourly = "https://api.weather.gov/forecast/hourly/1",
                forecastGridData = "https://api.weather.gov/forecast/grid/1",
                relativeLocation = mockRelativeLocation
            )
        )
        val mockForecastPeriod = ForecastPeriod(
            number = 1,
            name = "Today",
            temperature = 72,
            temperatureUnit = "F",
            windSpeed = "5 mph",
            windDirection = "N",
            icon = "https://api.weather.gov/icons/land/day/sunny",
            shortForecast = "Sunny",
            detailedForecast = "Sunny with clear skies",
            startTime = "2025-09-10T04:20:10+00:00"
        )
        val mockForecastResponse = ForecastResponse(
            properties = ForecastProperties(
                periods = listOf(mockForecastPeriod)
            )
        )
        val expectedTemperature = CurrentWeather(72, TemperatureUnit.FAHRENHEIT, "San Jose, CA")

        coEvery { weatherApi.getPoints(any(), any()) } returns mockPointsResponse
        coEvery { weatherApi.getForecast(any()) } returns mockForecastResponse
        every {
            mapper.mapToCurrentWeather(
                mockForecastPeriod,
                location = "San Jose, CA",
                description = "Sunny",
                latitude = 37.2883,
                longitude = -121.8434,
            )
        } returns expectedTemperature

        // When
        val result = repository.getCurrentWeather()

        // Then
        assertTrue(result.isSuccess)
        assertEquals(expectedTemperature, result.getOrNull())

        coVerify { weatherApi.getPoints(37.2883, -121.8434) }
        coVerify { weatherApi.getForecast("https://api.weather.gov/forecast/1") }
        verify {
            mapper.mapToCurrentWeather(
                mockForecastPeriod,
                location = "San Jose, CA",
                description = "Sunny",
                latitude = 37.2883,
                longitude = -121.8434,
            )
        }
    }

    @Test
    fun `getCurrentTemperature returns failure when API call fails`() = runTest {
        // Given
        val exception = RuntimeException("Network error")
        coEvery { weatherApi.getPoints(any(), any()) } throws exception

        // When
        val result = repository.getCurrentWeather()

        // Then
        assertTrue(result.isFailure)
        assertEquals(exception.message, result.exceptionOrNull()?.message)
    }

    @Test
    fun `getCurrentTemperature returns failure when no forecast periods available`() = runTest {
        // Given
        val mockPointsResponse = PointsResponse(
            properties = PointsProperties(
                forecast = "https://api.weather.gov/forecast/1",
                forecastHourly = "https://api.weather.gov/forecast/hourly/1",
                forecastGridData = "https://api.weather.gov/forecast/grid/1"
            )
        )
        val mockForecastResponse = ForecastResponse(
            properties = ForecastProperties(
                periods = emptyList()
            )
        )

        coEvery { weatherApi.getPoints(any(), any()) } returns mockPointsResponse
        coEvery { weatherApi.getForecast(any()) } returns mockForecastResponse

        // When
        val result = repository.getCurrentWeather()

        // Then
        assertTrue(result.isFailure)
        assertEquals("No forecast data available", result.exceptionOrNull()?.message)
    }
}