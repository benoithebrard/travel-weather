package com.bempaaa.travelweather.data.repository

import com.bempaaa.travelweather.data.model.CurrentWeatherForecast
import com.bempaaa.travelweather.data.service.WeatherForecastService
import com.bempaaa.travelweather.utils.NetworkResult

class WeatherForecastRepository(
    private val service: WeatherForecastService
) : CachedRepository<CurrentWeatherForecast>() {

    suspend fun getCurrentWeather(
        query: String
    ): NetworkResult<CurrentWeatherForecast> = performRequest(query) {
        service.currentWeather(query)
    }
}