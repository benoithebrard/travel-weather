package com.bempaaa.travelweather.utils

import android.view.View
import androidx.core.view.doOnLayout
import androidx.core.view.doOnNextLayout
import androidx.core.view.isVisible
import com.bempaaa.travelweather.utils.dimension.DimensionHolder

/**
 * This function briefly expands the view in order to get its dimensions
 * The client will receive a callback once a valid measurement has been made
 *
 * A cached dimension might be provided to reduce UI glitches
 */
inline fun computeExpandedDimensions(
    parentView: View,
    expandableView: View,
    cachedDimensions: DimensionHolder? = null,
    crossinline onMeasured: (DimensionHolder) -> Unit
) {
    if (cachedDimensions != null) {
        onMeasured(cachedDimensions)
        return
    }

    parentView.doOnLayout { view ->
        val originalHeight = view.height

        expandableView.visibility = View.INVISIBLE

        parentView.doOnNextLayout { expandedView ->
            val expandedHeight = expandedView.height
            parentView.post { expandableView.isVisible = false }

            if (originalHeight > 0 && expandedHeight > 0) {
                onMeasured(
                    DimensionHolder(
                        originalHeight,
                        expandedHeight
                    )
                )
            }
        }

        parentView.post { parentView.requestLayout() }
    }
}