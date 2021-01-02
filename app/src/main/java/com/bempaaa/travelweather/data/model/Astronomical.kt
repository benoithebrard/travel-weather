package com.bempaaa.travelweather.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Astronomical(
    @SerialName("sunrise") val sunrise: String,
    @SerialName("sunset") val sunset: String,
)