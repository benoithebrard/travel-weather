package com.bempaaa.travelweather.utils.extensions

import com.bempaaa.travelweather.data.model.HourWeather
import java.text.SimpleDateFormat
import java.util.*

private val hourDateFormat = SimpleDateFormat("h a", Locale.US)

val HourWeather.timeString: String
    get() = hourDateFormat.format(time * 1000L)