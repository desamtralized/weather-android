package com.recurly.weatherui.uiwidget.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun getAdaptivePadding(): Dp {
    val configuration = LocalConfiguration.current
    return when {
        configuration.screenWidthDp >= 840 -> 32.dp
        configuration.screenWidthDp >= 600 -> 24.dp
        else -> 16.dp
    }
}

@Composable
fun getAdaptiveSpacing(): Dp {
    val configuration = LocalConfiguration.current
    return when {
        configuration.screenWidthDp >= 840 -> 24.dp
        configuration.screenWidthDp >= 600 -> 16.dp
        else -> 8.dp
    }
}

@Composable
fun getAdaptiveIconSize(): Dp {
    val configuration = LocalConfiguration.current
    return when {
        configuration.screenWidthDp >= 840 -> 64.dp
        configuration.screenWidthDp >= 600 -> 48.dp
        else -> 40.dp
    }
}

@Composable
fun getAdaptiveCardElevation(): Dp {
    val configuration = LocalConfiguration.current
    return when {
        configuration.screenWidthDp >= 840 -> 6.dp
        configuration.screenWidthDp >= 600 -> 4.dp
        else -> 2.dp
    }
}