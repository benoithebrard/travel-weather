package com.bempaaa.travelweather.utils.extensions

import com.bempaaa.travelweather.data.model.DayForecast
import java.text.SimpleDateFormat
import java.util.*

private val simpleDateFormat = SimpleDateFormat("MMM d", Locale.US)

val DayForecast.dateString: String
    get() = simpleDateFormat.format(date * 1000L)