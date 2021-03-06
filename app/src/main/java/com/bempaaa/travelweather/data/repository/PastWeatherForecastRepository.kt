package com.bempaaa.travelweather.data.repository

import com.bempaaa.travelweather.data.model.PastWeatherForecast
import com.bempaaa.travelweather.data.service.WeatherForecastService
import com.bempaaa.travelweather.utils.RequestResult
import com.bempaaa.travelweather.utils.getPastDate
import com.bempaaa.travelweather.utils.memory.MemoryCacheUseCases.Companion.THIRTY_SEC
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.zip

class PastWeatherForecastRepository(
    private val service: WeatherForecastService
) : CachedRepository<PastWeatherForecast>() {

    fun getForecastDaysFlow(
        query: String,
        scope: CoroutineScope
    ): Flow<RequestResult<PastWeatherForecast>> {

        val day1Date = getPastDate(3)
        val day2Date = getPastDate(2)
        val day3Date = getPastDate(1)

        val flowDay1 = getForecastDayFlow(
            query = query,
            date = day1Date,
            scope = scope
        )

        val flowDay2 = getForecastDayFlow(
            query = query,
            date = day2Date,
            scope = scope
        )

        val flowDay3 = getForecastDayFlow(
            query = query,
            date = day3Date,
            scope = scope
        )

        val flow1And2: Flow<RequestResult<PastWeatherForecast>> =
            flowDay1.zip(flowDay2) { day1, day2 ->
                zipDayFlows(day1, day2)
            }

        return flow1And2.zip(flowDay3) { day1And2, day3 ->
            zipDayFlows(day1And2, day3)
        }
    }

    private fun getForecastDayFlow(
        query: String,
        date: String,
        scope: CoroutineScope
    ): Flow<RequestResult<PastWeatherForecast>> = scope.createFlowOf(
        key = query,
        pollingInterval = THIRTY_SEC
    ) {
        service.pastWeather(query, date)
    }

    private fun zipDayFlows(
        day1: RequestResult<PastWeatherForecast>,
        day2: RequestResult<PastWeatherForecast>
    ) = if (day1 is RequestResult.Success<PastWeatherForecast>) {
        if (day2 is RequestResult.Success<PastWeatherForecast>) {
            RequestResult.Success(
                day1.data.copy(
                    forecast = day1.data.forecast.copy(
                        dayForecasts = day1.data.forecast.dayForecasts
                            .plus(day2.data.forecast.dayForecasts)
                    )
                )
            )
        } else day2
    } else day1
}

fun CoroutineScope.getForecastDaysFlow(
    query: String,
    repository: PastWeatherForecastRepository
): Flow<RequestResult<PastWeatherForecast>> = repository.getForecastDaysFlow(
    query = query,
    scope = this
)