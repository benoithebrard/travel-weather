package com.bempaaa.travelweather.data.repository

import com.bempaaa.travelweather.data.model.PastWeatherForecast
import com.bempaaa.travelweather.data.service.WeatherForecastService
import com.bempaaa.travelweather.utils.RequestResult
import com.bempaaa.travelweather.utils.memory.MemoryCacheUseCases.Companion.THIRTY_SEC
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

class PastWeatherForecastRepository(
    private val service: WeatherForecastService
) : CachedRepository<PastWeatherForecast>() {

    fun getWeatherForecastFlow(
        query: String,
        date: String,
        scope: CoroutineScope
    ): Flow<RequestResult<PastWeatherForecast>> = scope.createFlowOf(
        key = query,
        pollingInterval = THIRTY_SEC
    ) {
        service.pastWeather(query, date)
    }
}