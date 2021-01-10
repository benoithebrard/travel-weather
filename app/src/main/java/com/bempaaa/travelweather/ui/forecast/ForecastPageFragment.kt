package com.bempaaa.travelweather.ui.forecast

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bempaaa.travelweather.R
import com.bempaaa.travelweather.config.AppConfig
import com.bempaaa.travelweather.data.model.FutureWeatherForecast
import com.bempaaa.travelweather.data.model.PastWeatherForecast
import com.bempaaa.travelweather.data.repository.getForecastDaysFlow
import com.bempaaa.travelweather.utils.RequestResult
import com.bempaaa.travelweather.utils.extensions.requireAppConfig
import kotlinx.android.synthetic.main.fragment_forecast_page.*
import kotlinx.coroutines.FlowPreview
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
                    }
                    UiState.Content -> {
                        swipe_container.isRefreshing = false
                        loading_indicator.isVisible = false
                        empty_indicator.isVisible = false
                        error_indicator.isVisible = false
                        swipe_container.isVisible = true
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

        setupViewListeners()
        setupRecyclerView()

        forecastViewModel.dayForecastViewModels.observe(this) { viewModels ->
            uiState = if (viewModels.isNotEmpty()) {
                UiState.Content
            } else UiState.Empty

            if (uiState == UiState.Content) {
                forecasts_list.adapter?.notifyDataSetChanged()
            }
        }

        fetchFutureForecasts()
    }

    private fun setupRecyclerView() {
        forecasts_list.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = ForecastDaysAdapter(forecastViewModel.dayForecastViewModels)
        }
    }

    private fun setupViewListeners() {
        retry_button.setOnClickListener {
            fetchFutureForecasts()
        }
        swipe_container.setOnRefreshListener {
            fetchPastForecasts()
        }
    }

    private fun fetchFutureForecasts() {
        uiState = UiState.Loading
        lifecycleScope.launchWhenResumed {
            getForecastDaysFlow(
                query = forecastLocation,
                repository = appConfig.futureForecastRepository
            ).collect { result ->
                when (result) {
                    is RequestResult.Success<FutureWeatherForecast> -> {
                        with(result.data) {
                            forecastViewModel.setFutureForecasts(
                                forecast.dayForecasts
                            )
                        }
                    }
                    is RequestResult.Error -> {
                        uiState = UiState.Error
                    }
                }
            }
        }
    }

    @OptIn(FlowPreview::class)
    private fun fetchPastForecasts() {
        lifecycleScope.launchWhenResumed {
            getForecastDaysFlow(
                query = forecastLocation,
                repository = appConfig.pastForecastRepository
            ).collect { result ->
                when (result) {
                    is RequestResult.Success<PastWeatherForecast> -> {
                        with(result.data) {
                            forecastViewModel.setPastForecasts(
                                forecast.dayForecasts
                            )
                        }
                        disableSwipeToRefresh()
                    }
                    is RequestResult.Error -> {
                        uiState = UiState.Error
                    }
                }
                swipe_container.isRefreshing = false
            }
        }
    }

    private fun disableSwipeToRefresh() {
        swipe_container.setOnRefreshListener {
            swipe_container.isRefreshing = false
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