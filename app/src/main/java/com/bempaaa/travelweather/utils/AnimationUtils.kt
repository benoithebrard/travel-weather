package com.bempaaa.travelweather.utils

import android.animation.TimeInterpolator
import android.animation.ValueAnimator
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import com.bempaaa.travelweather.utils.helper.Dimensions

inline fun createValueAnimator(
    isForward: Boolean = true,
    animationDuration: Long = 300L,
    animationInterpolator: TimeInterpolator = AccelerateDecelerateInterpolator(),
    crossinline onUpdate: (progress: Float) -> Unit
): ValueAnimator =
    ValueAnimator.ofFloat(
        if (isForward) 0f else 1f,
        if (isForward) 1f else 0f
    ).apply {
        addUpdateListener { animator ->
            onUpdate(animator.animatedValue as Float)
        }
        duration = animationDuration
        interpolator = animationInterpolator
    }

fun adjustViewHeight(
    parentView: View,
    dimensions: Dimensions,
    progress: Float
) {
    val originalHeight = dimensions.originalHeight
    val expandedHeight = dimensions.expandedHeight
    val newHeight =
        (originalHeight + (expandedHeight - originalHeight) * progress).toInt()

    parentView.layoutParams.height = newHeight
    parentView.requestLayout()
}