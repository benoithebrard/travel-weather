package com.bempaaa.travelweather.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DayForecast(
    @SerialName("date_epoch") val date: Long,
    @SerialName("day") val dayForecast: DayWeather,
    @SerialName("astro") val astronomical: Astronomical,
    @SerialName("hour") val hourForecasts: List<HourWeather>
)