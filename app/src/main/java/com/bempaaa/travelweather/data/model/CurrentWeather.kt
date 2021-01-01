package com.bempaaa.travelweather.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CurrentWeather(
    @SerialName("last_updated_epoch") val lastUpdated: Long,
    @SerialName("temp_c") val temperature: Double,
    @SerialName("condition") val condition: WeatherCondition,
    @SerialName("wind_kph") val windSpeed: Double,
    @SerialName("wind_dir") val windDirection: String,
    @SerialName("precip_mm") val precipitation: Double,
    @SerialName("humidity") val humidity: Int,
    @SerialName("cloud") val cloudiness: Int,
    @SerialName("feelslike_c") val feltTemperature: Double,
    @SerialName("uv") val uv: Double
)