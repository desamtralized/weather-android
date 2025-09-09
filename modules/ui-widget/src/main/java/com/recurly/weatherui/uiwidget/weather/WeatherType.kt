package com.recurly.weatherui.uiwidget.weather

import com.recurly.weatherui.uiwidget.R
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

/**
 * Weather condition types with associated Lottie animations.
 * @property animationRes Resource ID for weather animation
 */
enum class WeatherType(val animationRes: Int) {
    CLEAR_DAY(R.raw.clear_day),
    CLEAR_NIGHT(R.raw.clear_night),
    CLOUDY_DAY(R.raw.cloudy_day),
    CLOUDY_NIGHT(R.raw.cloudy_night),
    RAIN(R.raw.rain),
    STORM(R.raw.storm)
}

/** Parses weather descriptions into animation types */
object WeatherParser {
    /**
     * Maps forecast text to appropriate weather animation.
     * @param shortForecast Weather description from API
     * @param startTime ISO timestamp for day/night detection
     * @return Matching weather type with time-appropriate variant
     */
    fun parseWeather(shortForecast: String?, startTime: String?): WeatherType {
        if (shortForecast == null) return getDefaultWeatherForTime(startTime)
        
        val forecast = shortForecast.lowercase()
        val isNight = isNightTime(startTime)
        
        return when {
            forecast.contains("storm") || 
            forecast.contains("thunder") || 
            forecast.contains("lightning") -> WeatherType.STORM
            
            forecast.contains("rain") || 
            forecast.contains("shower") || 
            forecast.contains("drizzle") || 
            forecast.contains("precipitation") -> WeatherType.RAIN
            
            forecast.contains("cloud") || 
            forecast.contains("overcast") || 
            forecast.contains("fog") || 
            forecast.contains("mist") -> if (isNight) WeatherType.CLOUDY_NIGHT else WeatherType.CLOUDY_DAY
            
            forecast.contains("clear") || 
            forecast.contains("sunny") || 
            forecast.contains("fair") -> if (isNight) WeatherType.CLEAR_NIGHT else WeatherType.CLEAR_DAY
            
            forecast.contains("partly") -> if (isNight) WeatherType.CLOUDY_NIGHT else WeatherType.CLOUDY_DAY
            
            else -> getDefaultWeatherForTime(startTime)
        }
    }
    
    /** Determines if timestamp represents nighttime hours (8pm-6am) */
    fun isNightTime(startTime: String?): Boolean {
        if (startTime == null) return false
        
        return try {
            val dateTime = OffsetDateTime.parse(startTime, DateTimeFormatter.ISO_OFFSET_DATE_TIME)
            val hour = dateTime.hour
            hour < 6 || hour >= 20
        } catch (e: Exception) {
            false
        }
    }
    
    /** Provides time-appropriate default weather animation */
    private fun getDefaultWeatherForTime(startTime: String?): WeatherType {
        return if (isNightTime(startTime)) WeatherType.CLEAR_NIGHT else WeatherType.CLEAR_DAY
    }
}