package com.recurly.weatherui.data.utils

object TemperatureUtils {
    fun celsiusToFahrenheit(celsius: Int): Int {
        return (celsius * 9.0 / 5.0 + 32).toInt()
    }
    
    fun fahrenheitToCelsius(fahrenheit: Int): Int {
        return ((fahrenheit - 32) * 5.0 / 9.0).toInt()
    }
    
    fun convertTemperature(
        value: Int,
        fromUnit: TemperatureUnit,
        toUnit: TemperatureUnit
    ): Int {
        if (fromUnit == toUnit) return value
        
        return when {
            fromUnit == TemperatureUnit.CELSIUS && toUnit == TemperatureUnit.FAHRENHEIT -> 
                celsiusToFahrenheit(value)
            fromUnit == TemperatureUnit.FAHRENHEIT && toUnit == TemperatureUnit.CELSIUS -> 
                fahrenheitToCelsius(value)
            else -> value
        }
    }
}

enum class TemperatureUnit(val symbol: String) {
    CELSIUS("C"),
    FAHRENHEIT("F")
}