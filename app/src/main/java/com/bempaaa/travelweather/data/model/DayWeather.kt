package com.bempaaa.travelweather.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DayWeather(
    @SerialName("maxtemp_c") val maxTemperature: Double,
    @SerialName("mintemp_c") val minTemperature: Double,
    @SerialName("avgtemp_c") val temperature: Double,
    @SerialName("condition") val condition: WeatherCondition,
    @SerialName("maxwind_kph") val maxWindSpeed: Double,
    @SerialName("totalprecip_mm") val precipitation: Double,
    @SerialName("avghumidity") val humidity: Double,
    @SerialName("uv") val uv: Double,
    @SerialName("daily_chance_of_rain") val rain: Int,
    @SerialName("daily_chance_of_snow") val snow: Int
)
