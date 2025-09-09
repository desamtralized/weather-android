package com.recurly.weatherui.uiwidget.preview

import android.content.res.Configuration
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.recurly.weatherui.uiwidget.components.TemperatureWidget
import com.recurly.weatherui.uiwidget.state.TemperatureUiState
import com.recurly.weatherui.uiwidget.theme.WeatherUITheme

@Preview(name = "Phone Portrait", device = Devices.PHONE)
@Preview(name = "Phone Landscape", device = "spec:width=891dp,height=411dp")
@Preview(name = "Tablet", device = Devices.TABLET)
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun TemperatureWidgetSuccessPreview() {
    WeatherUITheme {
        Surface {
            TemperatureWidget(
                state = TemperatureUiState.Success(
                    temperature = 72,
                    unit = "F",
                    location = "San Jose, CA",
                    lastUpdated = "2:45 PM",
                    description = "Partly Cloudy"
                )
            )
        }
    }
}

@Preview(name = "Loading State")
@Composable
fun TemperatureWidgetLoadingPreview() {
    WeatherUITheme {
        Surface {
            TemperatureWidget(
                state = TemperatureUiState.Loading
            )
        }
    }
}

@Preview(name = "Error State")
@Composable
fun TemperatureWidgetErrorPreview() {
    WeatherUITheme {
        Surface {
            TemperatureWidget(
                state = TemperatureUiState.Error(
                    message = "Unable to load temperature data",
                    canRetry = true
                ),
                onRetry = {}
            )
        }
    }
}

@Preview(name = "Error State - No Retry")
@Composable
fun TemperatureWidgetErrorNoRetryPreview() {
    WeatherUITheme {
        Surface {
            TemperatureWidget(
                state = TemperatureUiState.Error(
                    message = "Service unavailable",
                    canRetry = false
                )
            )
        }
    }
}

@Preview(name = "High Temperature", device = Devices.PHONE)
@Composable
fun TemperatureWidgetHighTempPreview() {
    WeatherUITheme {
        Surface {
            TemperatureWidget(
                state = TemperatureUiState.Success(
                    temperature = 105,
                    unit = "F",
                    location = "Phoenix, AZ",
                    lastUpdated = "3:30 PM",
                    description = "Sunny"
                )
            )
        }
    }
}

@Preview(name = "Low Temperature", device = Devices.PHONE)
@Composable
fun TemperatureWidgetLowTempPreview() {
    WeatherUITheme {
        Surface {
            TemperatureWidget(
                state = TemperatureUiState.Success(
                    temperature = -15,
                    unit = "F",
                    location = "Minneapolis, MN",
                    lastUpdated = "8:00 AM",
                    description = "Snow"
                )
            )
        }
    }
}

@Preview(name = "Celsius", device = Devices.PHONE)
@Composable
fun TemperatureWidgetCelsiusPreview() {
    WeatherUITheme {
        Surface {
            TemperatureWidget(
                state = TemperatureUiState.Success(
                    temperature = 22,
                    unit = "C",
                    location = "London, UK",
                    lastUpdated = "14:45",
                    description = "Overcast"
                )
            )
        }
    }
}