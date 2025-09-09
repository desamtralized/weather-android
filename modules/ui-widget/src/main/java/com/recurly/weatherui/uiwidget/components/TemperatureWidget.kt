package com.recurly.weatherui.uiwidget.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import com.recurly.weatherui.uiwidget.state.TemperatureUiState

@Composable
fun TemperatureWidget(
    state: TemperatureUiState,
    modifier: Modifier = Modifier,
    onRetry: () -> Unit = {}
) {
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp
    val isCompact = screenWidthDp < 600
    val isTablet = screenWidthDp >= 600

    Box(
        modifier = modifier
            .fillMaxWidth()
            .semantics { contentDescription = "Weather temperature widget" }
    ) {
        when (state) {
            is TemperatureUiState.Loading -> {
                LoadingContent(isCompact)
            }
            is TemperatureUiState.Success -> {
                SuccessContent(
                    state = state,
                    isCompact = isCompact,
                    isTablet = isTablet
                )
            }
            is TemperatureUiState.Error -> {
                ErrorContent(
                    message = state.message,
                    canRetry = state.canRetry,
                    onRetry = onRetry,
                    isCompact = isCompact
                )
            }
        }
    }
}

@Composable
private fun SuccessContent(
    state: TemperatureUiState.Success,
    isCompact: Boolean,
    isTablet: Boolean
) {
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    if (isTablet && isLandscape) {
        TabletLandscapeTemperatureCard(state)
    } else if (isTablet) {
        TabletPortraitTemperatureCard(state)
    } else if (isLandscape) {
        PhoneLandscapeTemperatureCard(state)
    } else {
        PhonePortraitTemperatureCard(state)
    }
}