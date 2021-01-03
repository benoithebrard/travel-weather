package com.bempaaa.travelweather.utils

import android.view.View
import androidx.core.view.doOnLayout
import androidx.core.view.doOnNextLayout
import androidx.core.view.isVisible
import com.bempaaa.travelweather.utils.helper.Dimensions

/**
 * This function briefly expands the view in order to get its dimensions
 * The client will receive a callback once a valid measurement has been made
 *
 * A cached dimension might be provided to reduce UI glitches
 */
inline fun computeExpandedDimensions(
    parentView: View,
    expandableView: View,
    cachedDimensions: Dimensions? = null,
    crossinline onMeasured: (Dimensions) -> Unit
) {
    if (cachedDimensions != null) {
        onMeasured(cachedDimensions)
        return
    }

    parentView.doOnLayout { view ->
        val originalHeight = view.height
        val originalWidth = view.width

        expandableView.isVisible = true

        parentView.doOnNextLayout { expandedView ->
            val expandedHeight = expandedView.height
            val expandedWidth = expandedView.width

            parentView.post {
                expandableView.isVisible = false
            }

            if (originalHeight > 0 && expandedHeight > 0 &&
                originalWidth > 0 && expandedWidth > 0
            ) {
                onMeasured(
                    Dimensions(
                        originalHeight,
                        originalWidth,
                        expandedHeight,
                        expandedWidth
                    )
                )
            }
        }

        parentView.post { parentView.requestLayout() }
    }
}