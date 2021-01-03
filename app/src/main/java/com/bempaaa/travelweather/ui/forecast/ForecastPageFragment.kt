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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        forecastViewModel = ViewModelProvider(this).get(ForecastPageViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        forecasts_list.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = ForecastDaysAdapter(forecastViewModel.dayForecastViewModels)
        }

        forecastViewModel.dayForecastViewModels.observe(this) { viewModels ->
            refreshUIState(viewModels)
        }

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
                        val tot = result.e
                    }
                }
            }
        }
    }

    private fun refreshUIState(viewModels: List<ForecastDayViewModel>) {
        loading_indicator.isVisible = viewModels.isEmpty()
        forecasts_list.isVisible = viewModels.isNotEmpty()
        forecasts_list.adapter?.notifyDataSetChanged()
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