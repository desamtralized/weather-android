package com.recurly.weatherui.uiwidget.utils

/** Utility for generating Mapbox static map URLs */
object MapboxUtils {
    // mapbox publishable key, in prod env we could implement temp keys rotation with a backend if needed.
    private const val MAPBOX_API_KEY = "pk.eyJ1Ijoic2FtYmFyYm96YSIsImEiOiJjaXF5ZmJoMjQwMXM1ZnVtZ2NvdWFueXFwIn0.lH4JQ6MffArH4f5DJITvaA"
    private const val BASE_URL = "https://api.mapbox.com/styles/v1"

    /** Available map rendering styles */
    enum class MapStyle(val styleId: String) {
        DAY("mapbox/navigation-day-v1"),
        NIGHT("mapbox/navigation-night-v1")
    }

    /**
     * Generates static map image URL for location background.
     * @param latitude Map center latitude
     * @param longitude Map center longitude
     * @param width Image width in pixels
     * @param height Image height in pixels
     * @param zoom Map zoom level (1-20)
     * @param isDarkMode App dark theme state
     * @param isNightTime Weather timestamp indicates night
     * @return Formatted Mapbox Static API URL
     */
    fun generateStaticMapUrl(
        latitude: Double,
        longitude: Double,
        width: Int,
        height: Int,
        zoom: Int = 12,
        isDarkMode: Boolean = false,
        isNightTime: Boolean = false
    ): String {
        val style = when {
            isDarkMode -> MapStyle.NIGHT
            isNightTime -> MapStyle.NIGHT
            else -> MapStyle.DAY
        }

        return buildString {
            append(BASE_URL)
            append("/${style.styleId}/static/")
            append("${longitude},${latitude},${zoom},0,0/")
            append("${width}x${height}@2x")
            append("?access_token=$MAPBOX_API_KEY")
            append("&attribution=false")
            append("&logo=false")
        }
    }
}
