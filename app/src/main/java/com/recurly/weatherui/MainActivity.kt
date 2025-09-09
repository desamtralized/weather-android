package com.recurly.weatherui

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import com.recurly.weatherui.navigation.WeatherNavigation
import com.recurly.weatherui.ui.theme.WeatherUITheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
open class MainActivity : ComponentActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        enableEdgeToEdge()
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_USER
        
        setContent {
            WeatherUITheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    WeatherNavigation()
                }
            }
        }
    }
    
    protected open fun enableEdgeToEdge() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
    }
}