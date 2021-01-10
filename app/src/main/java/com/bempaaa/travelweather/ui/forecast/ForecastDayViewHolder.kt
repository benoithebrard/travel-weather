package com.bempaaa.travelweather.ui.forecast

import android.view.View
import androidx.core.animation.doOnCancel
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bempaaa.travelweather.utils.adjustViewHeight
import com.bempaaa.travelweather.utils.computeExpandedDimensions
import com.bempaaa.travelweather.utils.createValueAnimator
import com.bempaaa.travelweather.utils.extensions.dateString
import com.bempaaa.travelweather.utils.extensions.fullIconUrl
import com.bempaaa.travelweather.utils.extensions.maxTempRounded
import com.bempaaa.travelweather.utils.extensions.minTempRounded
import com.bempaaa.travelweather.utils.helper.DimensionsHelper.forecastDimensions
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.list_item_forecast_day.view.*

class ForecastDayViewHolder(
    forecastDayView: View
) : RecyclerView.ViewHolder(forecastDayView) {

    var boundViewModel: ForecastDayViewModel? = null

    fun bind(viewModel: ForecastDayViewModel) {
        boundViewModel = viewModel

        with(viewModel.forecast) {
            itemView.date_text.text = dateString
            itemView.day_text.text = dayForecast.condition.text
            itemView.max_temp.text = "${dayForecast.maxTempRounded}°"
            itemView.min_temp.text = "${dayForecast.minTempRounded}°"
            Glide.with(itemView).load(dayForecast.condition.fullIconUrl).into(itemView.day_image)

            itemView.wind_value.text = "${dayForecast.maxWindSpeed} km/h"
            itemView.humidity_value.text = "${dayForecast.humidity} %"
            itemView.uv_value.text = "${dayForecast.uv}"
            itemView.sun_value.text = "${astronomical.sunrise}, ${astronomical.sunset}"
        }

        computeExpandedDimensions(
            parentView = itemView,
            expandableView = itemView.expandable_day_forecast_info,
            cachedDimensions = forecastDimensions
        ) { dimensions ->
            forecastDimensions = dimensions

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
}

internal fun ForecastDayViewHolder.isSameForecast(viewModel: ForecastDayViewModel): Boolean =
    viewModel.forecast == boundViewModel?.forecast