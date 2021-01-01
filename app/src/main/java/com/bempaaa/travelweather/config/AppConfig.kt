package com.bempaaa.travelweather.config

import android.content.Context
import com.bempaaa.travelweather.data.repository.WeatherForecastRepository
import com.bempaaa.travelweather.data.service.createWeatherForecastService
import kotlinx.serialization.ExperimentalSerializationApi
import okhttp3.OkHttpClient

class AppConfig(
    context: Context
) {
    private val httpClient: OkHttpClient = OkHttpClient.Builder().build()

    @OptIn(ExperimentalSerializationApi::class)
    private val forecastService = createWeatherForecastService(
        baseUrl = BASE_URL,
        httpClient = httpClient
    )

    val forecastRepository = WeatherForecastRepository(
        service = forecastService
    )

    companion object {
        private val BASE_URL = "http://api.weatherapi.com/v1/"
    }
}