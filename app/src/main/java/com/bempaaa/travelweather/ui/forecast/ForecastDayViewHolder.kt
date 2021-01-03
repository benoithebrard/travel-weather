package com.bempaaa.travelweather.ui.forecast

import android.animation.ValueAnimator
import android.view.View
import androidx.core.animation.doOnCancel
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bempaaa.travelweather.utils.computeExpandedDimensions
import com.bempaaa.travelweather.utils.createValueAnimator
import com.bempaaa.travelweather.utils.extensions.dateString
import com.bempaaa.travelweather.utils.extensions.fullIconUrl
import com.bempaaa.travelweather.utils.helper.Dimensions
import com.bempaaa.travelweather.utils.helper.DimensionsHelper.forecastDimensions
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.list_item_forecast_day.view.*

class ForecastDayViewHolder(
    forecastDayView: View
) : RecyclerView.ViewHolder(forecastDayView) {

    var lastViewModel: ForecastDayViewModel? = null

    fun bind(viewModel: ForecastDayViewModel) {
        lastViewModel = viewModel

        with(viewModel.forecast) {
            itemView.date_text.text = dateString
            itemView.day_text.text = dayForecast.condition.text
            itemView.max_temp.text = "${dayForecast.maxTemperature}°"
            itemView.min_temp.text = "${dayForecast.minTemperature}°"
            Glide.with(itemView).load(dayForecast.condition.fullIconUrl).into(itemView.day_image)
        }

        computeExpandedDimensions(
            parentView = itemView,
            expandableView = itemView.expandable_day_forecast_info,
            cachedDimensions = forecastDimensions
        ) { dimensions ->
            forecastDimensions = dimensions

            itemView.setOnClickListener {
                createExpansionAnimator(
                    shouldExpand = !viewModel.isExpanded,
                    viewModel = viewModel,
                    dimensions = dimensions
                ).start()
            }
        }
    }

    private fun createExpansionAnimator(
        shouldExpand: Boolean,
        viewModel: ForecastDayViewModel,
        dimensions: Dimensions
    ): ValueAnimator = createValueAnimator(
        isForward = shouldExpand
    ) { progress ->
        adjustExpandedHeight(dimensions, progress)
    }.apply {
        if (shouldExpand) {
            doOnStart {
                viewModel.setExpanded(true)
            }
        } else {
            doOnEnd {
                viewModel.setExpanded(false)
            }
        }
        doOnCancel {
            viewModel.setExpanded(false)
        }
    }

    private fun adjustExpandedHeight(
        dimensions: Dimensions,
        progress: Float
    ) {
        val originalHeight = dimensions.originalHeight
        val expandedHeight = dimensions.expandedHeight
        val newHeight =
            (originalHeight + (expandedHeight - originalHeight) * progress).toInt()

        itemView.layoutParams.height = newHeight
        itemView.requestLayout()
    }

    private fun ForecastDayViewModel.setExpanded(
        isExpanded: Boolean
    ) {
        itemView.expandable_day_forecast_info.isVisible = isExpanded
        this.isExpanded = isExpanded
    }
}