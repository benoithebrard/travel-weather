package com.bempaaa.travelweather.utils.extensions

import com.bempaaa.travelweather.data.model.WeatherCondition

val WeatherCondition.fullIconUrl: String
    get() = "http:$iconUrl"