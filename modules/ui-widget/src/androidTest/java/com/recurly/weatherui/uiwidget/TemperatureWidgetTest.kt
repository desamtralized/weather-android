package com.recurly.weatherui.uiwidget

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.recurly.weatherui.data.utils.TemperatureUnit
import com.recurly.weatherui.uiwidget.components.TemperatureWidget
import com.recurly.weatherui.uiwidget.state.TemperatureUiState
import com.recurly.weatherui.uiwidget.theme.WeatherUITheme
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TemperatureWidgetTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun temperatureWidget_displaysLoadingState() {
        composeTestRule.setContent {
            WeatherUITheme {
                TemperatureWidget(
                    state = TemperatureUiState.Loading
                )
            }
        }

        composeTestRule
            .onNodeWithText("Loading temperature...")
            .assertIsDisplayed()

        composeTestRule
            .onNode(hasProgressBarRangeInfo(ProgressBarRangeInfo.Indeterminate))
            .assertIsDisplayed()
    }

    @Test
    fun temperatureWidget_displaysSuccessState() {
        composeTestRule.setContent {
            WeatherUITheme {
                TemperatureWidget(
                    state = TemperatureUiState.Success(
                        temperature = 72,
                        unit = TemperatureUnit.FAHRENHEIT,
                        location = "San Jose, CA",
                        lastUpdated = "2:45 PM",
                        description = "Partly Cloudy"
                    )
                )
            }
        }

        // Check temperature is displayed (will be animated, so we check for the unit)
        composeTestRule.onNodeWithText("°F", substring = true).assertIsDisplayed()

        // Check location is displayed
        composeTestRule.onNodeWithText("San Jose, CA").assertIsDisplayed()

        // Check last updated time is displayed
        composeTestRule.onNodeWithText("2:45 PM", substring = true).assertIsDisplayed()

        // Check description is displayed
        composeTestRule.onNodeWithText("Partly Cloudy").assertIsDisplayed()
    }

    @Test
    fun temperatureWidget_handlesErrorStateWithRetry() {
        var retryClicked = false

        composeTestRule.setContent {
            WeatherUITheme {
                TemperatureWidget(
                    state = TemperatureUiState.Error(
                        message = "Failed to load temperature data",
                        canRetry = true
                    ),
                    onRetry = { retryClicked = true }
                )
            }
        }

        // Check error message is displayed
        composeTestRule
            .onNodeWithText("Failed to load temperature data")
            .assertIsDisplayed()

        // Check retry button is displayed
        composeTestRule
            .onNodeWithText("Try Again")
            .assertIsDisplayed()

        // Click retry button
        composeTestRule
            .onNodeWithText("Try Again")
            .performClick()

        // Verify retry callback was called
        assertTrue(retryClicked)
    }

    @Test
    fun temperatureWidget_handlesErrorStateWithoutRetry() {
        composeTestRule.setContent {
            WeatherUITheme {
                TemperatureWidget(
                    state = TemperatureUiState.Error(
                        message = "Service unavailable",
                        canRetry = false
                    )
                )
            }
        }

        // Check error message is displayed
        composeTestRule
            .onNodeWithText("Service unavailable")
            .assertIsDisplayed()

        // Check retry button is NOT displayed
        composeTestRule
            .onNodeWithText("Try Again")
            .assertDoesNotExist()
    }

    @Test
    fun temperatureWidget_hasCorrectSemantics() {
        composeTestRule.setContent {
            WeatherUITheme {
                TemperatureWidget(
                    state = TemperatureUiState.Success(
                        temperature = 72,
                        unit = TemperatureUnit.FAHRENHEIT,
                        location = "San Jose, CA",
                        lastUpdated = "2:45 PM"
                    )
                )
            }
        }

        // Check widget has correct content description
        composeTestRule
            .onNodeWithContentDescription("Weather temperature widget")
            .assertIsDisplayed()
    }

    @Test
    fun temperatureWidget_displaysNegativeTemperature() {
        composeTestRule.setContent {
            WeatherUITheme {
                TemperatureWidget(
                    state = TemperatureUiState.Success(
                        temperature = -15,
                        unit = TemperatureUnit.FAHRENHEIT,
                        location = "Minneapolis, MN",
                        lastUpdated = "8:00 AM",
                        description = "Snow"
                    )
                )
            }
        }

        // Check negative temperature can be displayed
        composeTestRule.onNodeWithText("°F", substring = true).assertIsDisplayed()
        composeTestRule.onNodeWithText("Minneapolis, MN").assertIsDisplayed()
        composeTestRule.onNodeWithText("Snow").assertIsDisplayed()
    }

    @Test
    fun temperatureWidget_displaysCelsius() {
        composeTestRule.setContent {
            WeatherUITheme {
                TemperatureWidget(
                    state = TemperatureUiState.Success(
                        temperature = 22,
                        unit = TemperatureUnit.CELSIUS,
                        location = "London, UK",
                        lastUpdated = "14:45"
                    )
                )
            }
        }

        // Check Celsius unit is displayed
        composeTestRule.onNodeWithText("°C", substring = true).assertIsDisplayed()
        composeTestRule.onNodeWithText("London, UK").assertIsDisplayed()
    }

    @Test
    fun temperatureWidget_handlesLongLocationName() {
        composeTestRule.setContent {
            WeatherUITheme {
                TemperatureWidget(
                    state = TemperatureUiState.Success(
                        temperature = 75,
                        unit = TemperatureUnit.FAHRENHEIT,
                        location = "Llanfairpwllgwyngyllgogerychwyrndrobwllllantysiliogogogoch, Wales",
                        lastUpdated = "12:00 PM"
                    )
                )
            }
        }

        // Check long location name is displayed
        composeTestRule
            .onNodeWithText("Llanfairpwllgwyngyllgogerychwyrndrobwllllantysiliogogogoch, Wales")
            .assertIsDisplayed()
    }
}