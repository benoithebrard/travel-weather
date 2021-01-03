package com.bempaaa.travelweather.data.repository

import com.bempaaa.travelweather.data.model.FutureWeatherForecast
import com.bempaaa.travelweather.data.service.WeatherForecastService
import com.bempaaa.travelweather.utils.RequestResult
import com.bempaaa.travelweather.utils.caching.MemoryCacheUseCases.Companion.ONE_MIN
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

class FutureWeatherForecastRepository(
    private val service: WeatherForecastService
) : CachedRepository<FutureWeatherForecast>() {

    fun getWeatherForecastFlow(
        query: String,
        scope: CoroutineScope
    ): Flow<RequestResult<FutureWeatherForecast>> = scope.createFlowOf(
        key = query,
        pollingInterval = ONE_MIN
    ) {
        service.futureWeather(query)
    }
}