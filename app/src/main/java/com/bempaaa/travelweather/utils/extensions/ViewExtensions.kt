package com.bempaaa.travelweather.utils.extensions

import android.view.View
import android.view.ViewGroup
import com.bempaaa.travelweather.utils.dimension.DimensionHolder

fun View.adjustHeight(
    dimension: DimensionHolder,
    progress: Float
) {
    val deltaHeight = dimension.expandedValue - dimension.originalValue
    val newHeight = (dimension.originalValue + deltaHeight * progress).toInt()

    layoutParams.height = newHeight
    requestLayout()
}

fun View.adjustHorizontalMargin(
    dimension: DimensionHolder,
    progress: Float
) {
    val deltaHeight = dimension.originalValue - dimension.expandedValue
    val newMargin = (dimension.originalValue - deltaHeight * progress).toInt()

    (layoutParams as? ViewGroup.MarginLayoutParams)?.apply {
        leftMargin = newMargin
        rightMargin = newMargin
    }
    requestLayout()
}