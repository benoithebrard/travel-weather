package com.bempaaa.travelweather.ui.forecast

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bempaaa.travelweather.data.model.DayForecast
import com.bempaaa.travelweather.utils.extensions.combineWith

class ForecastPageViewModel : ViewModel() {

    private val _futureForecasts = MutableLiveData<List<DayForecast>>()
    private val _pastForecasts = MutableLiveData<List<DayForecast>>()

    val dayForecastViewModels: LiveData<List<ForecastDayViewModel>> =
        _pastForecasts.combineWith(_futureForecasts) { past, future ->
            listOfNotNull(
                past?.map { ForecastDayViewModel(it) },
                future?.map { ForecastDayViewModel(it) }
            ).flatten()
        }

    fun setFutureForecasts(forecasts: List<DayForecast>) {
        _futureForecasts.value = forecasts
    }

    fun setPastForecasts(forecasts: List<DayForecast>) {
        _pastForecasts.value = forecasts
    }
}