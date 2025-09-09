package com.recurly.weatherui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.recurly.weatherui.data.models.CurrentWeather
import com.recurly.weatherui.data.repository.WeatherRepository
import com.recurly.weatherui.data.utils.TemperatureUnit
import com.recurly.weatherui.ui.viewmodel.WeatherViewModel
import com.recurly.weatherui.uiwidget.state.TemperatureUiState
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class WeatherViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @MockK
    private lateinit var weatherRepository: WeatherRepository

    private lateinit var viewModel: WeatherViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxed = true)
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `loadWeatherData updates UI state with success`() = runTest {
        // Given
        val temperature = CurrentWeather(72, TemperatureUnit.FAHRENHEIT, "San Jose, CA")
        coEvery { weatherRepository.getCurrentWeather() } returns Result.success(temperature)

        // When
        viewModel = WeatherViewModel(weatherRepository)
        advanceUntilIdle()

        // Then
        val state = viewModel.uiState.value
        assertTrue(state is TemperatureUiState.Success)
        assertEquals(72, (state as TemperatureUiState.Success).temperature)
        assertEquals(TemperatureUnit.FAHRENHEIT, state.unit)
        assertEquals("San Jose, CA", state.location)
    }

    @Test
    fun `loadWeatherData updates UI state with error on failure`() = runTest {
        // Given
        val errorMessage = "Network error"
        coEvery { weatherRepository.getCurrentWeather() } returns Result.failure(
            Exception(
                errorMessage
            )
        )

        // When
        viewModel = WeatherViewModel(weatherRepository)
        advanceUntilIdle()

        // Then
        val state = viewModel.uiState.value
        assertTrue(state is TemperatureUiState.Error)
        assertEquals(errorMessage, (state as TemperatureUiState.Error).message)
        assertTrue(state.canRetry)
    }

    @Test
    fun `refresh sets isRefreshing to true then false`() = runTest {
        // Given
        val temperature = CurrentWeather(72, TemperatureUnit.FAHRENHEIT, "San Jose, CA")
        coEvery { weatherRepository.getCurrentWeather() } returns Result.success(temperature)
        viewModel = WeatherViewModel(weatherRepository)
        advanceUntilIdle()

        // When
        viewModel.refresh()

        // Then - isRefreshing should be true initially
        assertTrue(viewModel.isRefreshing.value)

        // When processing completes
        advanceUntilIdle()

        // Then - isRefreshing should be false
        assertTrue(!viewModel.isRefreshing.value)
    }

    @Test
    fun `initial load shows loading state`() = runTest {
        // Given - setup mock to delay so we can observe loading state
        coEvery { weatherRepository.getCurrentWeather() } coAnswers {
            kotlinx.coroutines.delay(100)
            Result.success(CurrentWeather(72, TemperatureUnit.FAHRENHEIT, "San Jose, CA"))
        }

        // When creating a new ViewModel
        viewModel = WeatherViewModel(weatherRepository)

        // Then - initial state should be Loading
        val initialState = viewModel.uiState.value
        assertTrue(initialState is TemperatureUiState.Loading)
    }
}