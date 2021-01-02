package com.bempaaa.travelweather

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.viewpager.widget.ViewPager
import com.bempaaa.travelweather.config.AppConfig
import com.bempaaa.travelweather.data.model.CurrentWeatherForecast
import com.bempaaa.travelweather.ui.main.SectionsPagerAdapter
import com.bempaaa.travelweather.utils.RequestResult
import com.bempaaa.travelweather.utils.extensions.requireAppConfig
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect

class MainActivity : AppCompatActivity() {

    private val appConfig: AppConfig by lazy {
        requireAppConfig()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)
        val fab: FloatingActionButton = findViewById(R.id.fab)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        lifecycleScope.launchWhenResumed {
            val forecastFlow: Flow<RequestResult<CurrentWeatherForecast>> =
                appConfig.forecastRepository.getCurrentWeatherFlow(
                    query = "granada",
                    scope = this
                )

            forecastFlow.collect { result ->
                when (result) {
                    is RequestResult.Success<CurrentWeatherForecast> -> {
                        val obj: CurrentWeatherForecast = result.data
                    }
                    is RequestResult.Error -> {
                        val tot = result.e
                    }
                }
            }
        }
    }
}