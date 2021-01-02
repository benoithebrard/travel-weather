package com.bempaaa.travelweather.data.service

import com.bempaaa.travelweather.data.model.CurrentWeatherForecast
import com.bempaaa.travelweather.data.model.FutureWeatherForecast
import com.bempaaa.travelweather.data.model.PastWeatherForecast
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

private const val API_KEY = "8503cbf6c97b47e983e113611210101"
private const val NB_FORECAST_DAYS = 5

interface WeatherForecastService {

    @GET("current.json?key=$API_KEY")
    suspend fun currentWeather(@Query("q") query: String): Response<CurrentWeatherForecast>

    @GET("forecast.json?key=$API_KEY&days=$NB_FORECAST_DAYS")
    suspend fun futureWeather(@Query("q") query: String): Response<FutureWeatherForecast>

    @GET("history.json?key=$API_KEY")
    suspend fun pastWeather(
        @Query("q") query: String,
        @Query("dt") date: String
    ): Response<PastWeatherForecast>
}