package com.recurly.weatherui.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.recurly.weatherui.LocalRefreshTrigger
import com.recurly.weatherui.ui.viewmodel.WeatherViewModel
import com.recurly.weatherui.uiwidget.components.TemperatureWidget

@Composable
fun FireTVWeatherScreen(
    viewModel: WeatherViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val refreshTrigger = LocalRefreshTrigger.current
    val refreshValue by refreshTrigger

    // Trigger refresh when the refresh value changes
    LaunchedEffect(refreshValue) {
        if (refreshValue > 0) {
            viewModel.refresh()
        }
    }

    Scaffold { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            TemperatureWidget(
                state = uiState,
                onRetry = { viewModel.loadWeatherData() },
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}