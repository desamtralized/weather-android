package com.recurly.weatherui.uiwidget.components

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Thermostat
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.recurly.weatherui.uiwidget.state.TemperatureUiState
import com.recurly.weatherui.uiwidget.utils.MapboxUtils
import com.recurly.weatherui.uiwidget.weather.WeatherParser

@Composable
fun PhonePortraitTemperatureCard(
    state: TemperatureUiState.Success,
    modifier: Modifier = Modifier
) {
    val isDarkTheme = isSystemInDarkTheme()
    val isNightTime = WeatherParser.isNightTime(state.startTime)
    
    val mapUrl = MapboxUtils.generateStaticMapUrl(
        latitude = state.latitude,
        longitude = state.longitude,
        width = 400,
        height = 250,
        zoom = 11,
        isDarkMode = isDarkTheme,
        isNightTime = isNightTime
    )
    
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(mapUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.matchParentSize()
            )
            
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.surface.copy(alpha = 0.85f),
                                MaterialTheme.colorScheme.surface.copy(alpha = 0.75f)
                            )
                        )
                    )
            )
            
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    WeatherAnimation(
                        weatherType = WeatherParser.parseWeather(state.description, state.startTime),
                        size = 100.dp
                    )
                    AnimatedTemperatureText(
                        temperature = state.temperature,
                        unit = state.unit
                    )
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        text = state.location,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            shadow = Shadow(
                                color = Color.Black.copy(alpha = 0.25f),
                                offset = Offset(1f, 1f),
                                blurRadius = 2f
                            )
                        ),
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    state.description?.let {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                shadow = Shadow(
                                    color = Color.Black.copy(alpha = 0.25f),
                                    offset = Offset(1f, 1f),
                                    blurRadius = 2f
                                )
                            ),
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(top = 4.dp),
                            textAlign = TextAlign.Center
                        )
                    }

                    Text(
                        text = "Updated: ${state.lastUpdated}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun PhoneLandscapeTemperatureCard(
    state: TemperatureUiState.Success,
    modifier: Modifier = Modifier
) {
    val isDarkTheme = isSystemInDarkTheme()
    val isNightTime = WeatherParser.isNightTime(state.startTime)
    
    val mapUrl = MapboxUtils.generateStaticMapUrl(
        latitude = state.latitude,
        longitude = state.longitude,
        width = 600,
        height = 200,
        zoom = 11,
        isDarkMode = isDarkTheme,
        isNightTime = isNightTime
    )
    
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(mapUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.matchParentSize()
            )
            
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(
                        Brush.horizontalGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.surface.copy(alpha = 0.9f),
                                MaterialTheme.colorScheme.surface.copy(alpha = 0.8f),
                                MaterialTheme.colorScheme.surface.copy(alpha = 0.9f)
                            )
                        )
                    )
            )
            
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Thermostat,
                        contentDescription = null,
                        modifier = Modifier.size(40.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = state.location,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            shadow = Shadow(
                                color = Color.Black.copy(alpha = 0.25f),
                                offset = Offset(1f, 1f),
                                blurRadius = 2f
                            )
                        ),
                        textAlign = TextAlign.Center
                    )
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.weight(1.5f)
                ) {
                    Box(
                        contentAlignment = Alignment.Center
                    ) {
                        WeatherAnimation(
                            weatherType = WeatherParser.parseWeather(state.description, state.startTime),
                            size = 100.dp
                        )
                        AnimatedTemperatureText(
                            temperature = state.temperature,
                            unit = state.unit,
                            textSize = 56.sp
                        )
                    }
                    state.description?.let {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                shadow = Shadow(
                                    color = Color.Black.copy(alpha = 0.25f),
                                    offset = Offset(1f, 1f),
                                    blurRadius = 2f
                                )
                            ),
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }

                Column(
                    horizontalAlignment = Alignment.End,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "Updated",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = state.lastUpdated,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}

@Composable
fun TabletPortraitTemperatureCard(
    state: TemperatureUiState.Success,
    modifier: Modifier = Modifier
) {
    val isDarkTheme = isSystemInDarkTheme()
    val isNightTime = WeatherParser.isNightTime(state.startTime)
    
    val mapUrl = MapboxUtils.generateStaticMapUrl(
        latitude = state.latitude,
        longitude = state.longitude,
        width = 600,
        height = 400,
        zoom = 12,
        isDarkMode = isDarkTheme,
        isNightTime = isNightTime
    )
    
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(24.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Box {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(mapUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.matchParentSize()
            )
            
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(
                        Brush.radialGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.surface.copy(alpha = 0.95f),
                                MaterialTheme.colorScheme.surface.copy(alpha = 0.85f)
                            )
                        )
                    )
            )
            
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    WeatherAnimation(
                        weatherType = WeatherParser.parseWeather(state.description, state.startTime),
                        size = 200.dp
                    )
                    AnimatedTemperatureText(
                        temperature = state.temperature,
                        unit = state.unit,
                        textSize = 96.sp
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Outlined.LocationOn,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = state.location,
                        style = MaterialTheme.typography.headlineMedium.copy(
                            shadow = Shadow(
                                color = Color.Black.copy(alpha = 0.3f),
                                offset = Offset(2f, 2f),
                                blurRadius = 3f
                            )
                        )
                    )
                }

                state.description?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            shadow = Shadow(
                                color = Color.Black.copy(alpha = 0.25f),
                                offset = Offset(1f, 1f),
                                blurRadius = 2f
                            )
                        ),
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Last Updated: ${state.lastUpdated}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
fun TabletLandscapeTemperatureCard(
    state: TemperatureUiState.Success,
    modifier: Modifier = Modifier
) {
    val isDarkTheme = isSystemInDarkTheme()
    val isNightTime = WeatherParser.isNightTime(state.startTime)
    
    val mapUrl = MapboxUtils.generateStaticMapUrl(
        latitude = state.latitude,
        longitude = state.longitude,
        width = 800,
        height = 300,
        zoom = 12,
        isDarkMode = isDarkTheme,
        isNightTime = isNightTime
    )
    
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(24.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Box {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(mapUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.matchParentSize()
            )
            
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(
                        Brush.horizontalGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.surface.copy(alpha = 0.92f),
                                MaterialTheme.colorScheme.surface.copy(alpha = 0.85f),
                                MaterialTheme.colorScheme.surface.copy(alpha = 0.92f)
                            )
                        )
                    )
            )
            
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.LocationOn,
                        contentDescription = null,
                        modifier = Modifier.size(64.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = state.location,
                        style = MaterialTheme.typography.headlineMedium.copy(
                            shadow = Shadow(
                                color = Color.Black.copy(alpha = 0.3f),
                                offset = Offset(2f, 2f),
                                blurRadius = 3f
                            )
                        ),
                        textAlign = TextAlign.Center
                    )
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.weight(1.5f)
                ) {
                    Box(
                        contentAlignment = Alignment.Center
                    ) {
                        WeatherAnimation(
                            weatherType = WeatherParser.parseWeather(state.description, state.startTime),
                            size = 180.dp
                        )
                        AnimatedTemperatureText(
                            temperature = state.temperature,
                            unit = state.unit,
                            textSize = 96.sp
                        )
                    }
                    state.description?.let {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.bodyLarge.copy(
                                shadow = Shadow(
                                    color = Color.Black.copy(alpha = 0.25f),
                                    offset = Offset(1f, 1f),
                                    blurRadius = 2f
                                )
                            ),
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                }

                Column(
                    horizontalAlignment = Alignment.End,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "Last Updated",
                        style = MaterialTheme.typography.labelMedium
                    )
                    Text(
                        text = state.lastUpdated,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}