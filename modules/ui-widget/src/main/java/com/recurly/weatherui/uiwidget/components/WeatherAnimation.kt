package com.recurly.weatherui.uiwidget.components

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.recurly.weatherui.uiwidget.weather.WeatherType

@Composable
fun WeatherAnimation(
    weatherType: WeatherType,
    modifier: Modifier = Modifier,
    size: Dp = 120.dp
) {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(weatherType.animationRes)
    )
    
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever
    )
    
    LottieAnimation(
        composition = composition,
        progress = { progress },
        modifier = modifier.size(size)
    )
}