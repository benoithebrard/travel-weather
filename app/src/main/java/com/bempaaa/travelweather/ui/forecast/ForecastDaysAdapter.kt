package com.bempaaa.travelweather.ui.forecast

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.bempaaa.travelweather.R

class ForecastDaysAdapter(
    private val forecasts: LiveData<List<ForecastDayViewModel>>
) : RecyclerView.Adapter<ForecastDayViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = ForecastDayViewHolder(
        forecastDayView = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_forecast_day, parent, false)
    )

    override fun onBindViewHolder(
        holder: ForecastDayViewHolder,
        position: Int
    ) {
        forecasts.value?.let { viewModels ->
            val viewModel = viewModels[position]
            if (viewModel.forecast != holder.lastViewModel?.forecast) {
                holder.bind(
                    viewModel.copy(
                        forecast = viewModel.forecast,
                        isExpanded = holder.lastViewModel?.isExpanded ?: false
                    )
                )
            }
        }
    }

    override fun getItemCount(): Int = forecasts.value?.size ?: 0
}