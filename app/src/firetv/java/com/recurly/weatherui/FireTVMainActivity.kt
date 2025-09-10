package com.recurly.weatherui

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.KeyEvent
import android.view.WindowManager
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import com.recurly.weatherui.navigation.FireTVWeatherNavigation
import com.recurly.weatherui.ui.theme.WeatherUITheme
import dagger.hilt.android.AndroidEntryPoint

val LocalRefreshTrigger = staticCompositionLocalOf { mutableStateOf(0L) }

@AndroidEntryPoint
class FireTVMainActivity : MainActivity() {

    private val refreshTrigger = mutableStateOf(0L)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // FireTV-specific initialization
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        // Set TV-specific window flags
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        // Override setContent to provide the refresh trigger
        setContent {
            WeatherUITheme {
                CompositionLocalProvider(LocalRefreshTrigger provides refreshTrigger) {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        FireTVWeatherNavigation()
                    }
                }
            }
        }
    }

    /**
     * Handles remote control interactions
     * and refresh the weather data when pressing one of the main keys
     */
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return when (keyCode) {
            KeyEvent.KEYCODE_DPAD_CENTER,
            KeyEvent.KEYCODE_ENTER,
            KeyEvent.KEYCODE_NUMPAD_ENTER -> {
                // Trigger refresh by updating the state value
                refreshTrigger.value = System.currentTimeMillis()
                true
            }

            else -> super.onKeyDown(keyCode, event)
        }
    }

    override fun enableEdgeToEdge() {
        // Don't enable edge-to-edge for TV
    }
}