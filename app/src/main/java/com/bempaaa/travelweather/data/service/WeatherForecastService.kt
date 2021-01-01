package com.bempaaa.travelweather.data.service

import com.bempaaa.travelweather.data.model.CurrentWeatherForecast
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

private const val API_KEY = "8503cbf6c97b47e983e113611210101"

interface WeatherForecastService {

    @GET("current.json?key=$API_KEY")
    suspend fun currentWeather(@Query("q") query: String): Response<CurrentWeatherForecast>
}