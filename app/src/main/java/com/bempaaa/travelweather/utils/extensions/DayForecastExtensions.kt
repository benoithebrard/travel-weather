package com.bempaaa.travelweather.utils.extensions

import com.bempaaa.travelweather.data.model.DayForecast
import com.bempaaa.travelweather.ui.forecast.ForecastDayItem
import com.bempaaa.travelweather.ui.forecast.ForecastHourItem
import com.xwray.groupie.ExpandableGroup
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import java.text.SimpleDateFormat
import java.util.*

private val simpleDateFormat = SimpleDateFormat("MMM d", Locale.US)

val DayForecast.dateString: String
    get() = simpleDateFormat.format(date * 1000L)

fun List<DayForecast>.toGroupAdapter(): GroupAdapter<GroupieViewHolder> =
    GroupAdapter<GroupieViewHolder>().apply {
        forEach { dayForecast ->
            val group = ExpandableGroup(ForecastDayItem(dayForecast))
            dayForecast.hourForecasts.forEach { hourForecast ->
                group.add(ForecastHourItem(hourForecast))
            }
            add(group)
        }
    }