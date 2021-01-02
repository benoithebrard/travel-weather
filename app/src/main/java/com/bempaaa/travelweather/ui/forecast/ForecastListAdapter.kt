package com.bempaaa.travelweather.ui.forecast

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.bempaaa.travelweather.data.model.DayForecast

class ForecastListAdapter(
    private val data: LiveData<List<DayForecast>>
) : RecyclerView.Adapter<ForecastListViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ForecastListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ForecastListViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(
        holder: ForecastListViewHolder,
        position: Int
    ) {
        data.value?.let { holder.bind(it[position]) }
    }

    override fun getItemCount(): Int = data.value?.size ?: 0

}