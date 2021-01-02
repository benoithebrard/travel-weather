package com.bempaaa.travelweather.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PastWeatherForecast(
    @SerialName("forecast") val forecast: WeatherForecast
)

