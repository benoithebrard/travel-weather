package com.bempaaa.travelweather.ui.forecast.hour

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bempaaa.travelweather.R

class ForecastHoursAdapter(
    private val forecastHours: List<ForecastHourViewModel>
) : RecyclerView.Adapter<ForecastHourViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = ForecastHourViewHolder(
        forecastHourView = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_forecast_hour, parent, false)
    )

    override fun onBindViewHolder(
        viewHolder: ForecastHourViewHolder,
        position: Int
    ) {
        val viewModel = forecastHours[position]
        viewHolder.bind(viewModel)
    }

    override fun getItemCount(): Int = forecastHours.size
}