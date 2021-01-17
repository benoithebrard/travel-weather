package com.bempaaa.travelweather.utils

import java.text.SimpleDateFormat
import java.util.*

private val dayDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ROOT)

fun getPastDate(
    nbDaysPast: Int
): String {
    val date = Calendar.getInstance()   // 19-01-2018
    date.add(Calendar.DATE, -nbDaysPast)         // 12-01-2018

    return dayDateFormat.format(date.time)
}
