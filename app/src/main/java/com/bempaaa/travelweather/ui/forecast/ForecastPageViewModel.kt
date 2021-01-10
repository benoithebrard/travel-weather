package com.bempaaa.travelweather.ui.forecast

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.bempaaa.travelweather.data.model.DayForecast

class ForecastPageViewModel : ViewModel() {

    private val _forecasts = MutableLiveData<List<DayForecast>>()
    val forecasts: LiveData<List<DayForecast>> = _forecasts

    val dayForecastViewModels: LiveData<List<ForecastDayViewModel>> =
        Transformations.map(_forecasts) { forecasts ->
            forecasts.map { forecast ->
                ForecastDayViewModel(forecast)
            }
        }

    fun setForecasts(forecasts: List<DayForecast>) {
        _forecasts.value = forecasts
    }
}