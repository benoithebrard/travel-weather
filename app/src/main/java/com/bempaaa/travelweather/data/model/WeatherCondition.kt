package com.bempaaa.travelweather.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherCondition(
    @SerialName("text") val text: String,
    @SerialName("icon") val iconUrl: String
)