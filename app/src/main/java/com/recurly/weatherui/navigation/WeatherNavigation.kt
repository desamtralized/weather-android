package com.recurly.weatherui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.recurly.weatherui.ui.screens.WeatherScreen

@Composable
fun WeatherNavigation(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = "weather"
    ) {
        composable("weather") {
            WeatherScreen()
        }
        
        // Add more screens as needed
        composable("settings") {
            // SettingsScreen()
        }
    }
}