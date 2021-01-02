package com.bempaaa.travelweather.ui.forecast

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bempaaa.travelweather.R
import com.bempaaa.travelweather.data.model.DayForecast
import com.bempaaa.travelweather.utils.extensions.dateString

class ForecastListViewHolder(
    inflater: LayoutInflater,
    parent: ViewGroup
) : RecyclerView.ViewHolder(
    inflater.inflate(R.layout.list_item_forecast_day, parent, false)
) {
    private var dayTextView: TextView = itemView.findViewById(R.id.dayString)
    private var weatherTextView: TextView = itemView.findViewById(R.id.weatherText)

    fun bind(forecast: DayForecast) {
        dayTextView.text = forecast.dateString
        weatherTextView.text = forecast.dayForecast.condition.text
    }
}