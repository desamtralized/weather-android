package com.recurly.weatherui

import android.content.pm.ActivityInfo
import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GoogleMainActivity : MainActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Google-specific initialization
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_USER
    }
}