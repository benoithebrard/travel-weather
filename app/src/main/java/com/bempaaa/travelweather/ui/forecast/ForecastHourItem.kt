package com.bempaaa.travelweather.ui.forecast

import com.bempaaa.travelweather.R
import com.bempaaa.travelweather.data.model.HourWeather
import com.bempaaa.travelweather.utils.extensions.timeString
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.list_item_forecast_day.*

class ForecastHourItem(private val forecast: HourWeather) : Item() {

    override fun getLayout() = R.layout.list_item_forecast_day

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.dayString.text = forecast.timeString
        viewHolder.weatherText.text = forecast.condition.text
    }
}