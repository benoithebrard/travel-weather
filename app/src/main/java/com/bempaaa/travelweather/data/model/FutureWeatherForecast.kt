package com.bempaaa.travelweather.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FutureWeatherForecast(
    @SerialName("current") val currentWeather: CurrentWeather,
    @SerialName("forecast") val forecast: WeatherForecast
)

