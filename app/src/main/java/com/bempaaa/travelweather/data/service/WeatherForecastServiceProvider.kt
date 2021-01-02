package com.bempaaa.travelweather.data.service

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit

private val JSON_MEDIA_TYPE = "application/json".toMediaType()

@ExperimentalSerializationApi
fun createWeatherForecastService(
    baseUrl: String,
    httpClient: OkHttpClient
): WeatherForecastService {
    val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(httpClient)
        .addConverterFactory(Json {
            ignoreUnknownKeys = true
        }.asConverterFactory(JSON_MEDIA_TYPE))
        .build()

    return retrofit.create(WeatherForecastService::class.java)
}
