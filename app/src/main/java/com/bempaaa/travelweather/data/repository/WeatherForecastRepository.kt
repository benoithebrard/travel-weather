package com.bempaaa.travelweather.data.repository

import com.bempaaa.travelweather.data.model.CurrentWeatherForecast
import com.bempaaa.travelweather.data.service.WeatherForecastService
import com.bempaaa.travelweather.utils.RequestResult
import com.bempaaa.travelweather.utils.caching.MemoryCacheUseCases.Companion.THIRTY_SEC
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

class WeatherForecastRepository(
    private val service: WeatherForecastService
) : CachedRepository<CurrentWeatherForecast>() {

    suspend fun getCurrentWeather(
        query: String
    ): RequestResult<CurrentWeatherForecast> = performRequest(query) {
        service.currentWeather(query)
    }

    fun getCurrentWeatherFlow(
        query: String,
        scope: CoroutineScope
    ): Flow<RequestResult<CurrentWeatherForecast>> = scope.createFlowOf(
        key = query,
        pollingInterval = THIRTY_SEC
    ) {
        service.currentWeather(query)
    }
}