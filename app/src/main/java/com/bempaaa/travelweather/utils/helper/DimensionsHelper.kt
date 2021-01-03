package com.bempaaa.travelweather.utils.helper

object DimensionsHelper {

    var forecastDimensions: Dimensions? = null
}

data class Dimensions(
    val originalHeight: Int,
    val originalWidth: Int,
    val expandedHeight: Int,
    val expandedWidth: Int
)