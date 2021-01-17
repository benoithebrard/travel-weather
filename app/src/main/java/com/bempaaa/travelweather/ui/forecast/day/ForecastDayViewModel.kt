package com.bempaaa.travelweather.ui.forecast.day

import com.bempaaa.travelweather.data.model.DayForecast

data class ForecastDayViewModel(
    val forecast: DayForecast,
    var isExpanded: Boolean = false
)