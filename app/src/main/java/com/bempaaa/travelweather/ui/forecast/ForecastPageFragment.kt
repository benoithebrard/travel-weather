package com.bempaaa.travelweather.ui.forecast

import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bempaaa.travelweather.R
import com.bempaaa.travelweather.config.AppConfig
import com.bempaaa.travelweather.data.model.FutureWeatherForecast
import com.bempaaa.travelweather.utils.RequestResult
import com.bempaaa.travelweather.utils.extensions.requireAppConfig
import kotlinx.android.synthetic.main.fragment_forecast_page.*
import kotlinx.coroutines.flow.collect

class ForecastPageFragment : Fragment(R.layout.fragment_forecast_page) {

    private val appConfig: AppConfig by lazy {
        requireAppConfig()
    }

    private val forecastLocation: String
        get() = arguments?.getString(ARG_FORECAST_LOCATION) ?: throw IllegalArgumentException()

    private lateinit var forecastViewModel: ForecastPageViewModel

    private var uiState: UiState = UiState.Loading
        set(value) {
            if (value != field) {
                when (value) {
                    UiState.Loading -> {
                        loading_indicator.isVisible = true
                        empty_indicator.isVisible = false
                        error_indicator.isVisible = false
                        swipe_container.isVisible = false
                    }
                    UiState.Empty -> {
                        loading_indicator.isVisible = false
                        empty_indicator.isVisible = true
                        error_indicator.isVisible = false
                        swipe_container.isVisible = false
                    }
                    UiState.Error -> {
                        loading_indicator.isVisible = false
                        empty_indicator.isVisible = false
                        error_indicator.isVisible = true
                        swipe_container.isVisible = false
                        retry_button.setOnClickListener {
                            fetchFutureForecasts()
                        }
                    }
                    UiState.Content -> {
                        loading_indicator.isVisible = false
                        empty_indicator.isVisible = false
                        error_indicator.isVisible = false
                        swipe_container.isVisible = true
                        forecasts_list.adapter?.notifyDataSetChanged()
                    }
                }
                field = value
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        forecastViewModel = ViewModelProvider(this).get(ForecastPageViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        swipe_container.setOnRefreshListener {
            Handler().postDelayed({
                swipe_container.isRefreshing = false
            }, 3000L)
        }

        forecasts_list.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = ForecastDaysAdapter(forecastViewModel.dayForecastViewModels)
        }

        forecastViewModel.dayForecastViewModels.observe(this) { viewModels ->
            uiState = if (viewModels.isNotEmpty()) {
                UiState.Content
            } else UiState.Empty
        }

        fetchFutureForecasts()
    }

    private fun fetchFutureForecasts() {
        uiState = UiState.Loading
        lifecycleScope.launchWhenResumed {
            appConfig.futureForecastRepository.getWeatherForecastFlow(
                query = forecastLocation,
                scope = this
            ).collect { result ->
                when (result) {
                    is RequestResult.Success<FutureWeatherForecast> -> {
                        forecastViewModel.setForecasts(result.data.forecast.dayForecasts)
                    }
                    is RequestResult.Error -> {
                        uiState = UiState.Error
                    }
                }
            }
        }
    }

    private enum class UiState {
        Loading,
        Error,
        Empty,
        Content
    }

    companion object {
        private const val ARG_FORECAST_LOCATION = "weather_forecast_location"

        @JvmStatic
        fun newInstance(location: String): ForecastPageFragment {
            return ForecastPageFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_FORECAST_LOCATION, location)
                }
            }
        }
    }
}