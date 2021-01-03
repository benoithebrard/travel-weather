package com.bempaaa.travelweather.ui.forecast

import android.animation.ValueAnimator
import android.view.View
import androidx.core.animation.doOnCancel
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart
import androidx.core.view.doOnLayout
import androidx.core.view.doOnNextLayout
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bempaaa.travelweather.utils.createValueAnimator
import com.bempaaa.travelweather.utils.extensions.dateString
import kotlinx.android.synthetic.main.list_item_forecast_day.view.*

class ForecastDayViewHolder(
    forecastDayView: View
) : RecyclerView.ViewHolder(forecastDayView) {

    var lastViewModel: ForecastDayViewModel? = null
    private var originalHeight: Int = -1
    private var expandedHeight: Int = -1

    fun bind(viewModel: ForecastDayViewModel) {
        lastViewModel = viewModel

        with(viewModel.forecast) {
            itemView.day_text.text = dateString
            itemView.weather_text.text = dayForecast.condition.text
        }

        computeExpandedDimensions { dimensions ->
            if (dimensions == null) return@computeExpandedDimensions

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
        dimensions: Pair<Int, Int>
    ): ValueAnimator = createValueAnimator(
        isForward = !viewModel.isExpanded
    ) { progress ->
        val originalHeight = dimensions.first
        val expandedHeight = dimensions.second
        val newHeight =
            (originalHeight + (expandedHeight - originalHeight) * progress).toInt()

        itemView.layoutParams.height = newHeight
        itemView.requestLayout()
    }.apply {
        updateUI(shouldExpand, viewModel)
    }

    private fun ValueAnimator.updateUI(
        shouldExpand: Boolean,
        viewModel: ForecastDayViewModel
    ) {
        if (shouldExpand) {
            doOnStart {
                viewModel.toggleExpanded(true)
            }
        } else {
            doOnEnd {
                viewModel.toggleExpanded(false)
            }
        }
        doOnCancel {
            viewModel.toggleExpanded(false)
        }
    }

    private fun ForecastDayViewModel.toggleExpanded(
        isExpanded: Boolean
    ) {
        itemView.weather_text.isVisible = isExpanded
        this.isExpanded = isExpanded
    }

    private fun computeExpandedDimensions(
        onMeasured: (
            Pair<Int, Int>?
        ) -> Unit
    ) {
        if (originalHeight > 0 && expandedHeight > 0) {
            onMeasured(Pair(originalHeight, expandedHeight))
            return
        }

        itemView.doOnLayout { view ->
            originalHeight = view.height
            itemView.weather_text.isVisible = true

            itemView.doOnNextLayout { nextView ->
                expandedHeight = nextView.height
                nextView.post {
                    itemView.weather_text.isVisible = false
                }
                onMeasured(
                    if (originalHeight > 0 && expandedHeight > 0) {
                        Pair(originalHeight, expandedHeight)
                    } else null
                )
            }

            itemView.post { itemView.requestLayout() }
        }
    }
}