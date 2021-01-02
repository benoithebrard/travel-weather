package com.bempaaa.travelweather.ui.forecast

import com.bempaaa.travelweather.R
import com.bempaaa.travelweather.data.model.DayForecast
import com.bempaaa.travelweather.utils.extensions.dateString
import com.xwray.groupie.ExpandableGroup
import com.xwray.groupie.ExpandableItem
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.list_item_forecast_day.*

class ForecastDayItem(
    private val forecast: DayForecast
) : Item(), ExpandableItem {

    private lateinit var expandableGroup: ExpandableGroup

    override fun getLayout() = R.layout.list_item_forecast_day

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.dayString.text = forecast.dateString
        viewHolder.weatherText.text = forecast.dayForecast.condition.text

        viewHolder.itemView.setOnClickListener {
            expandableGroup.onToggleExpanded()
        }
    }

    override fun setExpandableGroup(onToggleListener: ExpandableGroup) {
        this.expandableGroup = onToggleListener
    }
}