package com.recurly.weatherui.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.recurly.weatherui.uiwidget.state.TemperatureUiState

@Composable
fun TVWeatherContent(
    state: TemperatureUiState,
    onRetry: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .padding(48.dp),
        contentAlignment = Alignment.Center
    ) {
        when (state) {
            is TemperatureUiState.Loading -> {
                TVLoadingContent()
            }
            is TemperatureUiState.Success -> {
                TVTemperatureCard(state)
            }
            is TemperatureUiState.Error -> {
                TVErrorContent(
                    message = state.message,
                    canRetry = state.canRetry,
                    onRetry = onRetry
                )
            }
        }
    }
}

@Composable
private fun TVTemperatureCard(state: TemperatureUiState.Success) {
    Card(
        modifier = Modifier
            .fillMaxWidth(0.7f)
            .focusable(true),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(48.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Weather icon
            Icon(
                imageVector = Icons.Default.Refresh,
                contentDescription = null,
                modifier = Modifier.size(120.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            
            // Temperature info
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "${state.temperature}°${state.unit.symbol}",
                    style = MaterialTheme.typography.displayLarge.copy(
                        fontSize = 96.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
                Text(
                    text = state.location,
                    style = MaterialTheme.typography.headlineLarge
                )
                Text(
                    text = "Updated: ${state.lastUpdated}",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }
    }
}

@Composable
private fun TVLoadingContent() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(80.dp),
            strokeWidth = 6.dp
        )
        Text(
            text = "Loading weather data...",
            style = MaterialTheme.typography.headlineMedium
        )
    }
}

@Composable
private fun TVErrorContent(
    message: String,
    canRetry: Boolean,
    onRetry: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text(
            text = "Error",
            style = MaterialTheme.typography.displayMedium,
            color = MaterialTheme.colorScheme.error
        )
        Text(
            text = message,
            style = MaterialTheme.typography.headlineSmall
        )
        if (canRetry) {
            Button(
                onClick = onRetry,
                modifier = Modifier
                    .padding(top = 16.dp)
                    .focusable(true)
            ) {
                Text(
                    text = "Try Again",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)
                )
            }
        }
    }
}