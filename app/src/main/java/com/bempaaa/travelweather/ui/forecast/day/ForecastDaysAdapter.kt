package com.bempaaa.travelweather.ui.forecast.day

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.bempaaa.travelweather.R

class ForecastDaysAdapter(
    private val forecastDays: LiveData<List<ForecastDayViewModel>>
) : RecyclerView.Adapter<ForecastDayViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = ForecastDayViewHolder(
        forecastDayView = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_forecast_day, parent, false)
    )

    override fun onBindViewHolder(
        viewHolder: ForecastDayViewHolder,
        position: Int
    ) {
        forecastDays.value?.let { viewModels ->
            val viewModel = viewModels[position]
            viewHolder.bind(viewModel)
        }
    }

    override fun getItemCount(): Int = forecastDays.value?.size ?: 0
}