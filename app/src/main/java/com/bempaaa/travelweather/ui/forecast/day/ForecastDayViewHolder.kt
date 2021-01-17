package com.bempaaa.travelweather.ui.forecast.day

import android.view.View
import androidx.core.animation.doOnCancel
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bempaaa.travelweather.ui.forecast.hour.ForecastHoursAdapter
import com.bempaaa.travelweather.ui.forecast.hour.toForecastHourViewModels
import com.bempaaa.travelweather.utils.adjustViewHeight
import com.bempaaa.travelweather.utils.computeExpandedDimensions
import com.bempaaa.travelweather.utils.createValueAnimator
import com.bempaaa.travelweather.utils.extensions.dateString
import com.bempaaa.travelweather.utils.extensions.fullIconUrl
import com.bempaaa.travelweather.utils.extensions.maxTempRounded
import com.bempaaa.travelweather.utils.extensions.minTempRounded
import com.bempaaa.travelweather.utils.helper.Dimensions
import com.bempaaa.travelweather.utils.helper.DimensionsHelper.forecastDimensions
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_forecast_expanded.view.*
import kotlinx.android.synthetic.main.item_forecast_main.view.*

class ForecastDayViewHolder(
    forecastDayView: View
) : RecyclerView.ViewHolder(forecastDayView) {

    fun bind(viewModel: ForecastDayViewModel) {
        setupView(viewModel)

        computeExpandedDimensions(
            parentView = itemView,
            expandableView = itemView.expandable_day_forecast_info,
            cachedDimensions = forecastDimensions
        ) { dimensions ->
            forecastDimensions = dimensions
            setupClickListener(viewModel, dimensions)
        }
    }

    private fun setupView(viewModel: ForecastDayViewModel) {
        with(viewModel.forecast) {
            itemView.apply {
                date_text.text = dateString
                day_text.text = dayForecast.condition.text
                max_temp.text = "${dayForecast.maxTempRounded}°"
                min_temp.text = "${dayForecast.minTempRounded}°"

                wind_value.text = "${dayForecast.maxWindSpeed} km/h"
                humidity_value.text = "${dayForecast.humidity} %"
                uv_value.text = "${dayForecast.uv}"
                sun_value.text = "${astronomical.sunrise}, ${astronomical.sunset}"

                forecast_hours.apply {
                    layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
                    adapter = ForecastHoursAdapter(hourForecasts.toForecastHourViewModels())
                }

                Glide.with(this).load(dayForecast.condition.fullIconUrl).into(day_image)
            }
        }
    }

    private fun setupClickListener(
        viewModel: ForecastDayViewModel,
        dimensions: Dimensions
    ) {
        itemView.setOnClickListener {
            val shouldExpand = !viewModel.isExpanded

            fun setExpanded(isExpanded: Boolean) {
                itemView.expandable_day_forecast_info.isVisible = isExpanded
                viewModel.isExpanded = isExpanded
            }

            createValueAnimator(
                isForward = shouldExpand
            ) { progress ->
                adjustViewHeight(
                    parentView = itemView,
                    dimensions = dimensions,
                    progress = progress
                )
            }.apply {
                if (shouldExpand) {
                    doOnStart { setExpanded(true) }
                } else {
                    doOnEnd { setExpanded(false) }
                }
                doOnCancel { setExpanded(false) }
            }.start()
        }
    }
}