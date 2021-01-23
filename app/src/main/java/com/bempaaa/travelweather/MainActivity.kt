package com.bempaaa.travelweather

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.bempaaa.travelweather.ui.destination.DestinationPickerFragment
import com.bempaaa.travelweather.ui.main.MainPagerFragment

class MainActivity : FragmentActivity(R.layout.activity_main), NavigationListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportFragmentManager.beginTransaction()
            .replace(R.id.root_container, MainPagerFragment())
            .commit()
    }

    override fun onFabClicked() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.root_container, DestinationPickerFragment())
            .addToBackStack(null)
            .commit()
    }

    override fun onBackClicked() = onBackPressed()

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        } else {
            super.onBackPressed()
        }
    }
}