package com.recurly.weatherui.uiwidget.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Shadow
import com.recurly.weatherui.data.utils.TemperatureUnit
import com.recurly.weatherui.data.utils.TemperatureUtils

@Composable
fun AnimatedTemperatureText(
    temperature: Int,
    unit: TemperatureUnit,
    modifier: Modifier = Modifier,
    textSize: TextUnit = 72.sp,
    onUnitToggle: ((TemperatureUnit) -> Unit)? = null,
) {
    var currentUnit by remember(unit) {
        mutableStateOf(unit)
    }

    val displayTemperature = if (unit != currentUnit) {
        TemperatureUtils.convertTemperature(
            temperature, 
            unit,
            currentUnit
        )
    } else {
        temperature
    }

    val animatedTemperature by animateIntAsState(
        targetValue = displayTemperature, animationSpec = tween(
            durationMillis = 1000, easing = FastOutSlowInEasing
        ), label = "temperature_animation"
    )

    Row(
        modifier = modifier.then(
            Modifier.clickable {
                currentUnit = when (currentUnit) {
                    TemperatureUnit.CELSIUS -> TemperatureUnit.FAHRENHEIT
                    TemperatureUnit.FAHRENHEIT -> TemperatureUnit.CELSIUS
                }
                onUnitToggle?.invoke(currentUnit)
            }), verticalAlignment = Alignment.Top
    ) {
        Text(
            text = "$animatedTemperature", style = TextStyle(
                fontSize = textSize,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                shadow = Shadow(
                    color = Color.Black.copy(alpha = 0.3f),
                    offset = Offset(2f, 2f),
                    blurRadius = 4f
                )
            )
        )
        Text(
            text = "°${currentUnit.symbol}", style = TextStyle(
                fontSize = textSize * 0.5f,
                fontWeight = FontWeight.Normal,
                color = MaterialTheme.colorScheme.primary,
                shadow = Shadow(
                    color = Color.Black.copy(alpha = 0.3f),
                    offset = Offset(1f, 1f),
                    blurRadius = 2f
                )
            ), modifier = Modifier.padding(top = 8.dp)
        )
    }
}