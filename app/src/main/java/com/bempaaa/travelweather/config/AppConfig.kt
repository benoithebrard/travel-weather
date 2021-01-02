package com.bempaaa.travelweather.config

import android.content.Context
import com.bempaaa.travelweather.data.repository.CurrentWeatherForecastRepository
import com.bempaaa.travelweather.data.repository.FutureWeatherForecastRepository
import com.bempaaa.travelweather.data.repository.PastWeatherForecastRepository
import com.bempaaa.travelweather.data.service.createWeatherForecastService
import kotlinx.serialization.ExperimentalSerializationApi
import okhttp3.OkHttpClient

/*
 * Application configuration used to access data repositories and other unique objects
 * There can only be 1 instance of this class
 */
class AppConfig(context: Context) {

    private val httpClient: OkHttpClient = OkHttpClient.Builder().build()

    @OptIn(ExperimentalSerializationApi::class)
    private val forecastService = createWeatherForecastService(
        baseUrl = BASE_URL,
        httpClient = httpClient
    )

    val currentForecastRepository = CurrentWeatherForecastRepository(
        service = forecastService
    )

    val futureForecastRepository = FutureWeatherForecastRepository(
        service = forecastService
    )

    val pastForecastRepository = PastWeatherForecastRepository(
        service = forecastService
    )

    companion object {
        private const val BASE_URL = "http://api.weatherapi.com/v1/"
    }
}