package com.bempaaa.travelweather.utils.extensions

import com.bempaaa.travelweather.data.model.DayWeather
import kotlin.math.roundToInt

val DayWeather.maxTempRounded: Int
    get() = maxTemperature.roundToInt()

val DayWeather.minTempRounded: Int
    get() = minTemperature.roundToInt()