package com.bempaaa.travelweather.utils.extensions

import android.view.View
import com.bempaaa.travelweather.utils.dimension.DimensionHolder

fun View.adjustHeight(
    dimension: DimensionHolder,
    progress: Float
) {
    with(dimension) {
        val deltaHeight = expandedValue - originalValue
        val newHeight = (originalValue + deltaHeight * progress).toInt()

        layoutParams.height = newHeight
        requestLayout()
    }
}