package com.recurly.weatherui.utils

import android.content.Context
import android.content.pm.PackageManager
import android.content.res.Configuration
import com.recurly.weatherui.BuildConfig

object PlatformUtils {
    
    fun isTV(context: Context): Boolean {
        return context.packageManager.hasSystemFeature(PackageManager.FEATURE_LEANBACK)
    }
    
    fun isTablet(context: Context): Boolean {
        val configuration = context.resources.configuration
        val screenLayout = configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK
        return screenLayout >= Configuration.SCREENLAYOUT_SIZE_LARGE
    }
    
    fun isLandscape(context: Context): Boolean {
        return context.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    }
    
    fun getPlatformType(): String {
        return BuildConfig.PLATFORM
    }
    
    fun isGooglePlatform(): Boolean {
        return BuildConfig.PLATFORM == "GOOGLE"
    }
    
    fun isFireTVPlatform(): Boolean {
        return BuildConfig.PLATFORM == "FIRETV"
    }
}