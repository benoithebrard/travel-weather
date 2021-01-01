package com.bempaaa.travelweather.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CurrentWeatherForecast(
    @SerialName("current") val currentWeather: CurrentWeather
)