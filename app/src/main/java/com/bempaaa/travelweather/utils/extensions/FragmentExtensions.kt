package com.bempaaa.travelweather.utils.extensions

import androidx.fragment.app.Fragment
import com.bempaaa.travelweather.config.AppConfig

fun Fragment.requireAppConfig(): AppConfig = requireActivity().requireAppConfig()