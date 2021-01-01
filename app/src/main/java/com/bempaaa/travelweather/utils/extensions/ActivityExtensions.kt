package com.bempaaa.travelweather.utils.extensions

import android.app.Activity
import com.bempaaa.travelweather.TravelWeatherApplication
import com.bempaaa.travelweather.config.AppConfig

fun Activity.requireAppConfig(): AppConfig = (application as TravelWeatherApplication).appConfig