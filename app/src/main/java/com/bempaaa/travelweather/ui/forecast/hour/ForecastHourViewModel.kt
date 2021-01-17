package com.bempaaa.travelweather.ui.forecast.hour

import com.bempaaa.travelweather.data.model.HourWeather

data class ForecastHourViewModel(
    val hour: HourWeather
)

fun List<HourWeather>.toForecastHourViewModels(): List<ForecastHourViewModel> = map { hourWeather ->
    ForecastHourViewModel(hourWeather)
}