package com.bempaaa.travelweather.ui.forecast.hour

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bempaaa.travelweather.utils.extensions.fullIconUrl
import com.bempaaa.travelweather.utils.extensions.tempRounded
import com.bempaaa.travelweather.utils.extensions.timeString
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.list_item_forecast_hour.view.*

class ForecastHourViewHolder(
    forecastHourView: View
) : RecyclerView.ViewHolder(forecastHourView) {

    fun bind(viewModel: ForecastHourViewModel) {
        with(viewModel.hour) {
            itemView.apply {
                hour_temp.text = "${tempRounded}°"
                hour_time.text = timeString
                Glide.with(this).load(condition.fullIconUrl).into(hour_image)
            }
        }
    }
}