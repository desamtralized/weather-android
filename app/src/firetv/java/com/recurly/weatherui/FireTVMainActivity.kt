package com.recurly.weatherui

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.KeyEvent
import android.view.WindowManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FireTVMainActivity : MainActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // FireTV-specific initialization
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        // Set TV-specific window flags
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }
    
    override fun enableEdgeToEdge() {
        // Don't enable edge-to-edge for TV
    }
    
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        // Handle TV remote control
        return when (keyCode) {
            KeyEvent.KEYCODE_DPAD_CENTER,
            KeyEvent.KEYCODE_ENTER -> {
                // Handle select button
                true
            }
            else -> super.onKeyDown(keyCode, event)
        }
    }
}