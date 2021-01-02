package com.bempaaa.travelweather.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HourWeather(
    @SerialName("time_epoch") val time: Long,
    @SerialName("temp_c") val temperature: Double,
    @SerialName("condition") val condition: WeatherCondition,
    @SerialName("wind_kph") val windSpeed: Double,
    @SerialName("wind_dir") val windDirection: String,
    @SerialName("precip_mm") val precipitation: Double,
    @SerialName("humidity") val humidity: Int,
    @SerialName("cloud") val cloudiness: Int,
    @SerialName("feelslike_c") val feltTemperature: Double,
    @SerialName("chance_of_rain") val rain: Int,
    @SerialName("chance_of_snow") val snow: Int
)