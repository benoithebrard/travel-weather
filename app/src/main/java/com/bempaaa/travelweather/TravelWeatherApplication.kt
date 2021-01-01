package com.bempaaa.travelweather

import android.app.Application
import com.bempaaa.travelweather.config.AppConfig

class TravelWeatherApplication : Application() {

    lateinit var appConfig: AppConfig

    override fun onCreate() {
        super.onCreate()
        appConfig = AppConfig(this)
    }
}