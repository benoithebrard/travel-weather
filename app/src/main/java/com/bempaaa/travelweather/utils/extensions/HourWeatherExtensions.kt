package com.bempaaa.travelweather.utils.extensions

import com.bempaaa.travelweather.data.model.HourWeather
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

private val hourDateFormat = SimpleDateFormat("hh:mm", Locale.US)

val HourWeather.timeString: String
    get() = hourDateFormat.format(time * 1000L)

val HourWeather.tempRounded: Int
    get() = temperature.roundToInt()