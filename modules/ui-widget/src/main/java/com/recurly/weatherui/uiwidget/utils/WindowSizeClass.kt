package com.recurly.weatherui.uiwidget.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration

@Composable
fun isCompactScreen(): Boolean {
    val configuration = LocalConfiguration.current
    return configuration.screenWidthDp < 600
}

@Composable
fun isMediumScreen(): Boolean {
    val configuration = LocalConfiguration.current
    return configuration.screenWidthDp in 600..839
}

@Composable
fun isExpandedScreen(): Boolean {
    val configuration = LocalConfiguration.current
    return configuration.screenWidthDp >= 840
}

@Composable
fun isTabletScreen(): Boolean {
    val configuration = LocalConfiguration.current
    return configuration.screenWidthDp >= 600
}

@Composable
fun isPhoneScreen(): Boolean {
    val configuration = LocalConfiguration.current
    return configuration.screenWidthDp < 600
}

@Composable
fun isTallScreen(): Boolean {
    val configuration = LocalConfiguration.current
    return configuration.screenHeightDp >= 900
}

@Composable
fun isMediumHeightScreen(): Boolean {
    val configuration = LocalConfiguration.current
    return configuration.screenHeightDp in 480..899
}

@Composable
fun isCompactHeightScreen(): Boolean {
    val configuration = LocalConfiguration.current
    return configuration.screenHeightDp < 480
}