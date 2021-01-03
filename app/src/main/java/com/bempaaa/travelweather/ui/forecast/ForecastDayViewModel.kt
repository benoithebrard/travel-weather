package com.bempaaa.travelweather.ui.forecast

import com.bempaaa.travelweather.data.model.DayForecast

data class ForecastDayViewModel(
    val forecast: DayForecast,
    var isExpanded: Boolean = false
)