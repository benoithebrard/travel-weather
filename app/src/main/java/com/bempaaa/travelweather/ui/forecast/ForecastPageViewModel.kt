package com.bempaaa.travelweather.ui.forecast

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bempaaa.travelweather.data.model.DayForecast

class ForecastPageViewModel : ViewModel() {

    private val _forecasts = MutableLiveData<List<DayForecast>>()
    val dayForecasts: LiveData<List<DayForecast>> = _forecasts

    fun setForecasts(forecasts: List<DayForecast>) {
        _forecasts.value = forecasts
    }
}