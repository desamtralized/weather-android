package com.recurly.weatherui

import android.app.Application
import android.os.StrictMode
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class WeatherApplication : Application() {
    
    override fun onCreate() {
        super.onCreate()
        
        // Initialize any app-wide configurations
        if (BuildConfig.DEBUG) {
            // Enable strict mode for debugging
            StrictMode.setThreadPolicy(
                StrictMode.ThreadPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .build()
            )
        }
    }
}